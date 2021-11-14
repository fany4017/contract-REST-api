package com.api.utility;

import com.api.dto.ContractDto;
import com.api.dto.ContractPremiumDto;
import com.api.dto.ContractUpdateDto;
import com.api.entity.Guarantee;
import com.api.repository.GuaranteeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("utility")
@AllArgsConstructor
public class ApiUtility {

    private final GuaranteeRepository guaranteeRepository;

    public ArrayList<String> selectGuaranteeList(ContractDto contractDto) {

        ArrayList<String> sub_guarantee_list = new ArrayList<>();

        for(int i=0;i<contractDto.getSub_guarantee_cd().length;i++){
            sub_guarantee_list.add(contractDto.getSub_guarantee_cd()[i]);
        }

        return sub_guarantee_list;
    }

    public ArrayList<String> selectGuaranteeList(ContractPremiumDto contractPremiumDto) {

        ArrayList<String> sub_guarantee_list = new ArrayList<>();

        for(int i=0;i<contractPremiumDto.getSub_guarantee_cd().length;i++){
            sub_guarantee_list.add(contractPremiumDto.getSub_guarantee_cd()[i]);
        }

        return sub_guarantee_list;
    }

    public ArrayList<String> selectGuaranteeList(ContractUpdateDto contractUpdateDto) {

        ArrayList<String> sub_guarantee_list = new ArrayList<>();

        for(int i=0;i<contractUpdateDto.getSub_guarantee_cd().length;i++){
            sub_guarantee_list.add(contractUpdateDto.getSub_guarantee_cd()[i]);
        }

        return sub_guarantee_list;
    }

    public String calcualteTotalFee(ArrayList<String> guaranteeList, int contract_term){

        double totalFee = 0;
        double totalSum = 0;

        //가입기간 x ((가입금액1 / 기준금액1) + (가입금액2 / 기준금액2))

        List<Guarantee> list = null;

        for(int i=0;i<guaranteeList.size();i++){
            list = guaranteeRepository.findBysubGuaranteeCd(guaranteeList.get(i).toString());
            totalSum += Float.valueOf((String)list.get(0).getJoinPrice())/ Float.valueOf((String)list.get(0).getStandardPrice());
        }

        totalFee = contract_term*totalSum;
        String result = String.format("%.2f", totalFee);

        return result;
    }

    public ArrayList<String> getGuaranteeName(ArrayList<String> guaranteeList){
        ArrayList<String> guaranteeNameList = new ArrayList<>();

        List<Guarantee> list = null;
        for(int i=0;i<guaranteeList.size();i++){
            list = guaranteeRepository.findBysubGuaranteeCd(guaranteeList.get(i).toString()); //세부담보의 이름을 가져옴
            guaranteeNameList.add(list.get(0).getSubGuaranteeName());
        }
        return guaranteeNameList;
    }
}
