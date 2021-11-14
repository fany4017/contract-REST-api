package com.api.repository;

import com.api.entity.Contract;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
public class ContractApiCallTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ContractRepository contractRepository;


    public void createContractList(){
        Contract contract = new Contract();
        contract.setId((long) 1);
        contract.setProductCd("P00001");
        contract.setContractTerm("10");
        contract.setContractStart("20210101");
        contract.setContractEnd("20211231");
        contract.setMainGuaranteeCd("travel");
        contract.setContractState("정상계약");
        contract.setTotalPremium("1500000");
        Contract saved = contractRepository.save(contract);
    }

    @Test
    @DisplayName("계약정보 생성 테스트")
    public void NO1_계약정보_생성테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\n" +
                                "\"product_cd\":\"P00002\",\n" +
                                "\"main_guarantee_cd\":\"cellphone\",\n" +
                                "\"sub_guarantee_cd\":[\"damaged_all\"],\n" +
                                "\"contract_term\" : \"10\",\n" +
                                "\"contract_start\" : \"20210101\",\n" +
                                "\"contract_end\" : \"20211231\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("예상보험료 조회 테스트")
    public void NO2_예상보험료_조회테스트() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/get/calculation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\n" +
                                "\"product_cd\":\"P00001\",\n" +
                                "\"main_guarantee_cd\":\"travel\",\n" +
                                "\"sub_guarantee_cd\":[\"airplane_delay\",\"traveler_injured\"],\n" +
                                "\"contract_term\" : \"10\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계약정보 조회 테스트")
    public void NO3_계약정보_조회테스트() throws Exception{
        this.createContractList();//계약을 하나 만들고
        mockMvc.perform(MockMvcRequestBuilders.get("/api/get/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("계약정보 수정 테스트")
    public void NO4_계약정보_수정테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/change/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\n" +
                                "\"main_guarantee_cd\":\"travel\",\n" +
                                "\"sub_guarantee_cd\":[\"traveler_injured\"],\n" +
                                "\"contract_term\" : \"5\",\n" +
                                "\"contract_state\" : \"기간만료\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("계약의 보유 담보 조회 테스트")
    public void NO5_계약_보유담보_조회테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/get/1/guarantee"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
