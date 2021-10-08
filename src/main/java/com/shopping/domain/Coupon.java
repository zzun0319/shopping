package com.shopping.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Coupon {

    @Id @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiscountType type; // 할인 타입: 고정금액, 할인율

    private Integer value; // 고정금액이면 얼마인지, 할인율이면 몇 퍼센트인지
    private LocalDateTime issueDate; // 쿠폰 발행일
    private LocalDateTime expireDate; // 쿠폰 만료일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 현재 쿠폰이 사용가능한지
     * @return
     */
    public boolean isUsable() {
        if(LocalDateTime.now().isBefore(getExpireDate())){
            return true;
        }
        return false;
    }

}
