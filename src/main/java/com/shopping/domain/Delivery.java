package com.shopping.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Delivery(Address address) {
        this.address = address;
        this.status = DeliveryStatus.BEFORE;
    }

    public void startDelivery() {
        this.status = DeliveryStatus.ING;
    }

    public void cancelDelivery() {
        this.status = DeliveryStatus.CANCEL;
    }

    public void completeDelivery() {
        this.status = DeliveryStatus.COMPLETE;
    }

    public String addressChange(Address address) {
        if(this.status == DeliveryStatus.BEFORE) {
            this.address = address;
            return "배송지 변경 완료";
        }
        return "배송 대기 상태에서만 변경이 가능합니다.";
    }
}
