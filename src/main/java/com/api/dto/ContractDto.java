package com.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter@Setter
public class ContractDto {

    private Long id;

    @NotNull
    @NotBlank
    private String product_cd;

    @NotNull
    @NotBlank
    private String main_guarantee_cd;

    private String[] sub_guarantee_cd;

    @NotNull
    @NotBlank
    private String contract_term;

    @Length(min = 8, max = 8)
    @NotNull
    @NotBlank
    private String contract_start;

    @Length(min = 8, max = 8)
    @NotNull
    @NotBlank
    private String contract_end;

    private String contract_state;

    private String total_premium;
}
