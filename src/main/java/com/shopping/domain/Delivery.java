package com.shopping.domain;

import com.shopping.domain.valuetypes.Address;
import com.shopping.enums.DeliveryStatus;
import com.shopping.exception.CannotChangeAddressException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void addressChange(Address address) {
        if(this.status != DeliveryStatus.BEFORE) {
            throw new CannotChangeAddressException("배송 시작 전에만 변경 가능합니다");
        }
        this.address = address;
    }
}
