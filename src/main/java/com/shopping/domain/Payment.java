package com.shopping.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Payment {

    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentOption option;

    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
