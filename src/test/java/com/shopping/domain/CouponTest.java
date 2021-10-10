package com.shopping.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CouponTest {

    @Test
    @DisplayName("기본 쿠폰 생성, 만료기간 전인 쿠폰이 생성 가능한지 확인한다")
    void isUsable() {

        // given
        Member member = Member.createMember("shopper1", "aaa1111", "aaa1111#");

        // when
        Coupon coupon = new Coupon(member); // 기본 쿠폰 생성

        // then
        Assertions.assertThat(coupon.isUsable()).isTrue();
        Assertions.assertThat(coupon.getValue()).isEqualTo(1000);
    }

    @Test
    void expired() {

        // given
        Member member = Member.createMember("shopper1", "aaa1111", "aaa1111#");

        // when
        Coupon coupon = new Coupon(LocalDateTime.of(2020, 3, 19, 12, 30), LocalDateTime.of(2021, 3, 19, 12, 30), member);

        // then
        Assertions.assertThat(coupon.isUsable()).isFalse();
    }
}