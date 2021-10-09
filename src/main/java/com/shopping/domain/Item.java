package com.shopping.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private Integer price;
    private Integer stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member salesman;

    /**
     * 누군가 상품을 주문했을 때 재고에서 주문수량만큼 감소시킨다.
     */
    public void reduceStockQuantity(int cnt) {
        this.stockQuantity -= cnt;
    }

    /**
     * 누군가 상품을 주문을 취소했을 때 취소한 수량만큼 재고를 증가시킨다.
     */
    public void addStockQuantity(int cnt) {
        this.stockQuantity += cnt;
    }
}
