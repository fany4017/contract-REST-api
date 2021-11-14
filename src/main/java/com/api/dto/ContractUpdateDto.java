package com.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter@Setter
public class ContractUpdateDto {

    @NotNull
    @NotBlank
    private String main_guarantee_cd;

    private String[] sub_guarantee_cd;

    @NotNull
    @NotBlank
    private String contract_term;

    @NotNull
    @NotBlank
    private String contract_state;

}
