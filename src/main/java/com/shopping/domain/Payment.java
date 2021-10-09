package com.shopping.domain;

import com.shopping.enums.PaymentOption;
import com.shopping.enums.PaymentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
