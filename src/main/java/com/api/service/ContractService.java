package com.api.service;

import com.api.code.ContractStatus;
import com.api.code.DefaultResponse;
import com.api.code.ResponseMessageCode;
import com.api.code.StatusCode;
import com.api.dto.ContractDto;
import com.api.dto.ContractPremiumDto;
import com.api.dto.ContractUpdateDto;
import com.api.entity.*;
import com.api.repository.ContractGuaranteeRepository;
import com.api.repository.ContractRepository;
import com.api.repository.ProductRepository;
import com.api.utility.ApiUtility;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ContractService {

    private final ContractRepository contractRepository;
    private final ProductRepository productRepository;
    private final ContractGuaranteeRepository contractGuaranteeRepository;
    private final ApiUtility utility;

    @PersistenceContext
    EntityManager em;

    // 1. 계약 신규 생성
    public ResponseEntity createContract(ContractDto contractDto){

        productRepository.findById(contractDto.getProduct_cd()).orElseThrow(()->new IllegalArgumentException(ResponseMessageCode.NOT_INCLUDED_PRODUCT_CD));
        if(productRepository.findByProductCdAndMainGuaranteeCd(contractDto.getProduct_cd(),contractDto.getMain_guarantee_cd()) == null){
            throw new IllegalArgumentException(ResponseMessageCode.NOT_INCLUDED_MAIN_GUARANTEE_CD);
        }

        //세부 담보코드가 주담보코드 하위에 속하는지 체크 시작
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QGuarantee qGuarantee = QGuarantee.guarantee;
        List<Guarantee> check = queryFactory.selectFrom(qGuarantee)
                .where(qGuarantee.subGuaranteeCd.in(contractDto.getSub_guarantee_cd()))
                .fetch();

        if(check.size() != contractDto.getSub_guarantee_cd().length){
            throw new IllegalArgumentException(ResponseMessageCode.NOT_INCLUDED_SUB_GUARUANTEE);
        }
        //세부 담보코드가 주담보코드 하위에 속하는지 체크 종료

        Contract contract = new Contract();

        contract.setProductCd(contractDto.getProduct_cd());
        contract.setMainGuaranteeCd(contractDto.getMain_guarantee_cd());
        contract.setContractTerm(contractDto.getContract_term());
        contract.setContractStart(contractDto.getContract_start());
        contract.setContractEnd(contractDto.getContract_end());
        contract.setContractState(ContractStatus.NORMAL.toString());

        //보험료 계산 시작
        ArrayList<String> sub_guarantee_list = utility.selectGuaranteeList(contractDto);
        String totalPremium = utility.calcualteTotalFee(sub_guarantee_list, Integer.parseInt(contractDto.getContract_term()));
        contract.setTotalPremium(totalPremium);
        //보험료 계산 종료

        //계약 정보 저장 시작
        Contract savedContract = contractRepository.save(contract);
        //계약 정보 저장 완료
        
        //계약-담보 정보 저장 시작
        ArrayList<String> guaranteeNameList = utility.getGuaranteeName(sub_guarantee_list);
        String sub_gurantee_cd = null;
        for(int i=0;i<sub_guarantee_list.size();i++){
            sub_gurantee_cd = sub_guarantee_list.get(i);
            ContractGuarantee contractGuarantee = new ContractGuarantee();
            ContractGuaranteeId id = new ContractGuaranteeId();

            id.setContractId(savedContract.getId().toString());
            id.setSubGuaranteeCd(sub_gurantee_cd);

            contractGuarantee.setContractGuaranteeId(id);
            contractGuarantee.setSubGuaranteeCd(sub_gurantee_cd);
            contractGuarantee.setSubGuaranteeName(guaranteeNameList.get(i));
            contractGuaranteeRepository.save(contractGuarantee);
        }
        //계약-담보 정보 저장 완료

        return new ResponseEntity(DefaultResponse.res(StatusCode.OK,
                ResponseMessageCode.SUCCESS_ADD_NEW_CONTRACT), HttpStatus.OK);
    }

    // 2. 계약 조회
    public ResponseEntity selectContract(Long id){
        Contract contract = contractRepository.findById(id).orElseThrow(()->new IllegalArgumentException(ResponseMessageCode.NOT_FOUND_CONTRACT_INFO));
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK,
                ResponseMessageCode.READ_CONTRACT_INFO,contract), HttpStatus.OK);
    }

    // 3. 예상 보험료 조회
    public ResponseEntity selectTotalPremium(ContractPremiumDto contractPremiumDto){
        productRepository.findById(contractPremiumDto.getProduct_cd()).orElseThrow(()->new IllegalArgumentException(ResponseMessageCode.NOT_INCLUDED_PRODUCT_CD));
        if(productRepository.findByProductCdAndMainGuaranteeCd(contractPremiumDto.getProduct_cd(),contractPremiumDto.getMain_guarantee_cd()) == null){
            throw new IllegalArgumentException(ResponseMessageCode.NOT_INCLUDED_MAIN_GUARANTEE_CD);
        }

        //세부 담보코드가 주담보코드 하위에 속하는지 체크 시작
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QGuarantee qGuarantee = QGuarantee.guarantee;
        List<Guarantee> check = queryFactory.selectFrom(qGuarantee)
                .where(qGuarantee.subGuaranteeCd.in(contractPremiumDto.getSub_guarantee_cd()))
                .fetch();

        if(check.size() != contractPremiumDto.getSub_guarantee_cd().length){
            throw new IllegalArgumentException(ResponseMessageCode.NOT_INCLUDED_SUB_GUARUANTEE);
        }
        //세부 담보코드가 주담보코드 하위에 속하는지 체크 종료

        //보험료 계산 시작
        ArrayList<String> sub_guarantee_list = utility.selectGuaranteeList(contractPremiumDto);
        String totalPremium = utility.calcualteTotalFee(sub_guarantee_list, Integer.parseInt(contractPremiumDto.getContract_term()));
        //보험료 계산 종료

        return new ResponseEntity(DefaultResponse.res(StatusCode.OK,
                ResponseMessageCode.READ_TOTAL_PREMIUM,totalPremium), HttpStatus.OK);
    }

    // 4. 계약 정보 수정
    public ResponseEntity updateContract(Long id, ContractUpdateDto contractUpdateDto){

        // 계약 ID가 DB에 존재하는지 확인
        Contract contract = contractRepository.findById(id).orElseThrow(()->new IllegalArgumentException(ResponseMessageCode.NOT_FOUND_CONTRACT_INFO));
        
        //계약 ID의 DB 주담보코드와 파라미터로 넘어온 주담보코드가 일치하는지 확인
        if(!contract.getMainGuaranteeCd().equals(contractUpdateDto.getMain_guarantee_cd())){
            throw new IllegalArgumentException(ResponseMessageCode.NOT_MATCHED_CONTRACT_ID_WITH_PARAMETER);
        }

        //세부 담보코드가 주담보코드 하위에 속하는지 체크 시작
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QGuarantee qGuarantee = QGuarantee.guarantee;
        List<Guarantee> check = queryFactory.selectFrom(qGuarantee)
                .where(qGuarantee.subGuaranteeCd.in(contractUpdateDto.getSub_guarantee_cd()))
                .fetch();

        if(check.size() != contractUpdateDto.getSub_guarantee_cd().length){
            throw new IllegalArgumentException(ResponseMessageCode.NOT_INCLUDED_SUB_GUARUANTEE);
        }
        //세부 담보코드가 주담보코드 하위에 속하는지 체크 종료

        //현재 계약의 계약상태가 기간만료인지 체크 시작
        String changedContractState = contract.getContractState();
        if(!contract.getContractState().equals("EXPIRED")){ //기간만료 상태가 아니면 파라미터로 넘어온 값으로 변경
            changedContractState = contractUpdateDto.getContract_state();
        }
        //현재 계약의 계약상태가 기간만료인지 체크 종료
        
        //보험료 계산 시작
        ArrayList<String> sub_guarantee_list = utility.selectGuaranteeList(contractUpdateDto);
        String totalPremium = utility.calcualteTotalFee(sub_guarantee_list, Integer.parseInt(contractUpdateDto.getContract_term()));
        //보험료 계산 종료

        //Contract 테이블에 계약기간(contract_term), 계약상태(contract_state), 총 보험료(total_premium) 업데이트 시작
        QContract qContract = QContract.contract;
        Long QContractAffected = queryFactory.update(qContract)
                .where(qContract.id.eq(id))
                .set(qContract.contractTerm,contractUpdateDto.getContract_term())
                .set(qContract.totalPremium,totalPremium)
                .set(qContract.contractState,changedContractState)
                .execute();
        //Contract 테이블에 계약기간(contract_term), 계약상태(contract_state), 총 보험료(total_premium) 업데이트 종료


        //계약-담보 관계 테이블 지우기 시작
        QContractGuarantee qContractGuarantee = QContractGuarantee.contractGuarantee;
        Long affected = queryFactory.delete(qContractGuarantee)
                .where(qContractGuarantee.contractGuaranteeId.contractId.eq(id.toString())) //계약 ID값 찾아서 삭제
                .execute();
        //계약-담보 관계 테이블 지우기 종료

        //계약-담보 관계 테이블에 인서트 시작
        ArrayList<String> guaranteeNameList = utility.getGuaranteeName(sub_guarantee_list);
        String sub_gurantee_cd = null;

        for(int i=0;i<sub_guarantee_list.size();i++){

            sub_gurantee_cd = sub_guarantee_list.get(i);

            ContractGuarantee contractGuarantee = new ContractGuarantee();
            ContractGuaranteeId contractGuaranteeId = new ContractGuaranteeId();

            contractGuaranteeId.setContractId(contract.getId().toString());
            contractGuaranteeId.setSubGuaranteeCd(sub_gurantee_cd);

            contractGuarantee.setContractGuaranteeId(contractGuaranteeId);
            contractGuarantee.setSubGuaranteeCd(sub_gurantee_cd);
            contractGuarantee.setSubGuaranteeName(guaranteeNameList.get(i));
            contractGuaranteeRepository.save(contractGuarantee);
        }
        //계약-담보 관계 테이블에 인서트 종료

        return new ResponseEntity(DefaultResponse.res(StatusCode.OK,
                ResponseMessageCode.SUCCESS_UPDATE_CONTRACT_INFO), HttpStatus.OK);

    }

    // 5. 계약 별 담보 조회
    public ResponseEntity selectContractGuarantee(Long id){
        ContractGuaranteeId contractGuaranteeId = new ContractGuaranteeId();
        contractGuaranteeId.setContractId(id.toString());
        List<ContractGuarantee> info = contractGuaranteeRepository.findByContractId(id.toString());

        List<HashMap<String, Object>> result = new ArrayList<>();

        for(int i=0;i<info.size();i++){
            HashMap<String, Object> map = new HashMap<String ,Object>();
            map.put("subGuaranteeCd",info.get(i).getSubGuaranteeCd());
            map.put("subGuaranteeName",info.get(i).getSubGuaranteeName());
            result.add(map);
        }
        return new ResponseEntity(DefaultResponse.res(StatusCode.OK,
                ResponseMessageCode.READ_CONTRACT_INFO,result), HttpStatus.OK);
    }
}
