package com.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class GuranteeDto {

    private String subGuaranteeCd;
    private String subGuaranteeName;
    private String mainGuaranteeCd;
    private String mainGuaranteeName;
    private String joinPrice;
    private String standardPrice;
}
