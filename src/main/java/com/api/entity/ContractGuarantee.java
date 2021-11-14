package com.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name="contract_guarantee_relationship")
public class ContractGuarantee implements Serializable{

    @EmbeddedId
    private ContractGuaranteeId contractGuaranteeId;

    @Column(insertable = false, updatable = false)
    private String contractId;
    /*@ManyToOne
    @JoinColumn(name = "contract_id" , insertable = false, updatable = false)
    private Contract contract;*/

    @Column(nullable = false, length = 50, insertable = false, updatable = false)
    private String subGuaranteeCd;

    private String subGuaranteeName;
}
