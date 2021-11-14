package com.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Product {

    @Id
    private String productCd;

    @Column(nullable = false, length = 50)
    private String productName;

    @Column(nullable = false, length = 50)
    private String mainGuaranteeCd;

    @Column(nullable = false, length = 10)
    private String productStart;

    @Column(nullable = false, length = 10)
    private String productEnd;

}
