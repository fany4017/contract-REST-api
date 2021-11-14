package com.api.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ContractGuaranteeId implements Serializable{

    //@Column(name = "contract_id")
    private String contractId;
    private String subGuaranteeCd;
}
