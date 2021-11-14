package com.api.repository;

import com.api.entity.Contract;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ContractRepositoryTest {

    @Autowired
    ContractRepository contractRepository;

    public void createContractList(){
        for(int i=1;i<=10;i++){
            Contract contract = new Contract();
            contract.setId((long) i);
            contract.setProductCd("P0000"+i);
            contract.setContractTerm("10"+1);
            contract.setContractStart("20210101");
            contract.setContractEnd("20211231");
            contract.setMainGuaranteeCd("travel");
            contract.setContractState("정상계약");
            contract.setTotalPremium("1500000"+i);
            Contract saved = contractRepository.save(contract);
        }
    }

    @Test
    @DisplayName("계약 신규 생성 테스트")
    public void createContractTest(){
        Contract contract = new Contract();
        contract.setId((long)1);
        contract.setProductCd("P00001");
        contract.setContractTerm("10");
        contract.setContractStart("20210101");
        contract.setContractEnd("20211231");
        contract.setMainGuaranteeCd("travel");
        contract.setContractState("정상계약");
        contract.setTotalPremium("1500000");
        Contract saved = contractRepository.save(contract);
        System.out.println(saved.toString());
    }
    
    @Test
    @DisplayName("계약 조회 테스트")
    public void selectContractTest(){
        this.createContractList();
        Optional<Contract> contract = contractRepository.findById(1L);
        System.out.println(contract.toString());
    }
}