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

    private Integer paidPrice;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public Payment(PaymentOption option) {
        this.option = option;
        this.paidPrice = 0;
        this.status = PaymentStatus.NOT_YET;
    }

    public void pay(Integer paidPrice) {
        this.paidPrice = paidPrice;
        this.status = PaymentStatus.COMPLETE;
    }

}
