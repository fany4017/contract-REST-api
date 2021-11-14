package com.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Guarantee {

    @Id
    @Column(nullable = false, length = 50)
    private String subGuaranteeCd;

    @Column(nullable = false, length = 50)
    private String subGuaranteeName;

    @Column(nullable = false, length = 50)
    private String mainGuaranteeCd;

    @Column(nullable = false, length = 50)
    private String mainGuaranteeName;

    @Column(nullable = false, length = 20)
    private String joinPrice;

    @Column(nullable = false, length = 20)
    private String standardPrice;

}
