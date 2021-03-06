package com.shopping.domain;

import com.shopping.enums.DeliveryStatus;
import com.shopping.enums.OrderStatus;
import com.shopping.exception.CannotCancelException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private Order(Member member, Delivery delivery, Payment payment) {
        this.member = member;
        this.delivery = delivery;
        this.payment = payment;
        this.status = OrderStatus.ORDER;
    }

    /**
     * 연관관계 양쪽에 세팅해주기 위한 메서드
     * @param orderItem
     */
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    /**
     * Order 생성 메서드
     * @param member 주문한 사람
     * @param delivery 배송 정보
     * @param payment 결제 정보
     * @param orderItems 주문 상품들
     * @return 필드를 채운 주문 객체
     */
    public static Order createOrder(Member member, Delivery delivery, Payment payment, OrderItem... orderItems){
        Order order = new Order(member, delivery, payment);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    /**
     * 주문 취소 메서드
     */
    public void cancelOrder() {

        if(delivery.getStatus() == DeliveryStatus.COMPLETE){ // 이미 배송완료라면 주문 취소 불가
            throw new CannotCancelException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        delivery.cancelDelivery();
        status = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public void pay() {
        payment.pay(getTotalOrderPrice());
        delivery.startDelivery();
    }

    /**
     * 각 주문 상품들의 개수 X 상품가격의 총합
     * @return
     */
    public int getTotalOrderPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

}
