package com.api.controller;

import com.api.dto.ContractDto;
import com.api.dto.ContractPremiumDto;
import com.api.dto.ContractUpdateDto;
import com.api.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class MainController {

    private final ContractService contractService;

    @PostMapping("/new")
    public ResponseEntity createContract(@Valid @RequestBody ContractDto contractDto) {
        return contractService.createContract(contractDto);
    }

    @GetMapping("/get/{id}") //계약정보 조회
    public ResponseEntity selectContract(@PathVariable Long id) {
        return contractService.selectContract(id);
    }

    @GetMapping("/get/calculation") //예상 총 보험료 계산
    public ResponseEntity calculatePremium(@Valid  @RequestBody ContractPremiumDto contractPremiumDto){
        return contractService.selectTotalPremium(contractPremiumDto);
    }

    @PutMapping("/change/{id}") //계약 정보 수정
    public ResponseEntity updateContract(@PathVariable Long id, @Valid  @RequestBody ContractUpdateDto contractUpdateDto){
        return contractService.updateContract(id, contractUpdateDto);
    }

    @GetMapping("/get/{id}/guarantee") //계약별 보유 담보 조회
    public ResponseEntity selectContractGuarantee(@PathVariable Long id) {
        return contractService.selectContractGuarantee(id);
    }

}
