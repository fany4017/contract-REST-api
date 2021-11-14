package com.api.entity;

import com.api.dto.ContractDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Contract {

    @Id
    @Column(name="contract_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@OneToMany
    //@JoinColumn(name="id")
    //private ContractGuarantee contractGuarantee; //계약-담보관계 엔티티애 1대다로 매핑

    @Column(nullable = false, length = 50)
    private String productCd;

    @Column(nullable = false, length = 50)
    private String mainGuaranteeCd;

    @Column(nullable = false, length = 10)
    private String contractTerm;

    @Column(nullable = false, length = 10)
    private String contractStart;

    @Column(nullable = false, length = 10)
    private String contractEnd;

    @Column(nullable = false, length = 20)
    private String totalPremium;

    @Column(nullable = false, length = 20)
    private String contractState;

}
