package com.shopping.domain.items;

import com.shopping.domain.Member;
import com.shopping.exception.CannotSaleItemException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    @DisplayName("아이템은 생성 메서드로 생성하고, 증감 메서드로 재고를 늘이고 줄인다.")
    void createItem() {

        // given
        Member member = Member.createMember("memberA", "aaa1111", "aaa#1111");
        member.permitSale("abc1234");

        Item item = Upper.createUpper("T-shirt1", 30000, 10, member, 32, 71, 58);

        // when
        item.reduceStockQuantity(3);
        // then
        org.assertj.core.api.Assertions.assertThat(item.getStockQuantity()).isEqualTo(7);

        // when
        item.addStockQuantity(3);
        // then
        org.assertj.core.api.Assertions.assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("허가 받지 않은 멤버는 판매 상품을 등록할 수 없다")
    void cannotSale() {

        // given, when
        Member member = Member.createMember("memberA", "aaa1111", "aaa#1111");

        // then
        Assertions.assertThrows(CannotSaleItemException.class, () -> Pants.createPants("Jean1", 50000, 3, member, 100, 28, 90));
    }

    @Test
    @DisplayName("상의, 바지, 외투 생성 확인")
    void testChild() {

        // given
        Member member = Member.createMember("memberA", "aaa1111", "aaa#1111");
        member.permitSale("abc1234");

        // when
        Pants jean1 = Pants.createPants("Jean1", 50000, 3, member, 100, 28, 90);
        Upper upper = Upper.createUpper("T-shirt1", 30000, 5, member, 32, 71, 58);
        Outer jacket1 = Outer.createOuter("Jacket1", 100000, 10, member, 100, 3, 45);

        // then
        org.assertj.core.api.Assertions.assertThat(jean1.getTotalLength()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(upper.getArmLength()).isEqualTo(32);
        org.assertj.core.api.Assertions.assertThat(jacket1.getWeight()).isEqualTo(3);
    }

}