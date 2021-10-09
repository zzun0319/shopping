package com.shopping.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer orderPrice; // 주문 상품의 가격
    private Integer orderQuantity; // 주문 수량

    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * OrderItem 생성 메서드
     * @param item 주문상품
     * @param orderPrice item의 price가 그대로 들어올 수도 있지만 쿠폰 할인 같은 게 적용된 가격이 들어올 수도 있다
     * @param orderQuantity 주문 수량
     * @return 필드를 채운 orderItem 객체
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int orderQuantity) { // item에 price가 있지만, 쿠폰 할인 같은 게 적용된 가격이 들어오도록
        OrderItem orderItem = new OrderItem();
        orderItem.item = item;
        orderItem.orderPrice = orderPrice;
        orderItem.orderQuantity = orderQuantity;

        item.reduceStockQuantity(orderQuantity);
        return orderItem;
    }

    /**
     * 취소 시 상품의 재고를 취소수량 만큼 다시 늘려준다.
     */
    public void cancel() {
        getItem().addStockQuantity(orderQuantity);
    }

    /**
     * 주문 상품의 가격 X 주문 수량
     * @return
     */
    public int getTotalPrice() {
        return getOrderPrice() * getOrderQuantity();
    }
}
