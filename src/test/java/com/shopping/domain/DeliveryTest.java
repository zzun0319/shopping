package com.shopping.domain;

import com.shopping.domain.valuetypes.Address;
import com.shopping.enums.DeliveryStatus;
import com.shopping.exception.CannotChangeAddressException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DeliveryTest {

    @Test
    @DisplayName("주문 생성 확인, 결제 전엔 주문이 시작되지 않은 상태이며 배송지를 바꿀 수 있다")
    void addressChange() {

        // given
        Delivery delivery = new Delivery(new Address("city1", "street1", "11111"));

        // when
        delivery.addressChange(new Address("city2", "street2", "@2222"));

        // then
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.BEFORE);
        assertThat(delivery.getAddress().getCity()).isEqualTo("city2");
    }

    @Test
    @DisplayName("배송이 완료된 상태에서는 배송지 변경이 불가능하다")
    void cannotChangeAddress() {

        // given
        Delivery delivery = new Delivery(new Address("city1", "street1", "11111"));

        // when
        delivery.completeDelivery();

        // then
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.COMPLETE);
        org.junit.jupiter.api.Assertions
                .assertThrows(CannotChangeAddressException.class, () -> delivery.addressChange(new Address("city2", "street2", "@2222")));
    }

}