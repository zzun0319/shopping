package com.shopping.domain;

import com.shopping.enums.DiscountType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiscountType type; // 할인 타입: 고정금액, 할인율
    private Integer value; // 고정금액이면 얼마인지, 할인율이면 몇 퍼센트인지

    private LocalDateTime issueDate; // 쿠폰 발행일
    private LocalDateTime expireDate; // 쿠폰 만료일

    private String description;

    /**
     * 발급 멤버만 받는 생성자
     * 쿠폰 발행일을 오늘, 현재 시간으로. 만료일을 30일 뒤로
     * 고정 금액 할인. 1000원
     */
    public Coupon(Member member) {
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        issueDate = LocalDateTime.of(today, currentTime);
        expireDate = LocalDateTime.of(today.plusDays(30), currentTime); // 30일 후
        type = DiscountType.FIX;
        value = 1000;
        this.member = member;
        description = "기본 쿠폰 1000원 할인";
    }

    /**
     *
     * @param type 할인 타입: 고정 금액인지 할인율인지
     * @param value 고정 금액이면 얼마인지, 할인율이면 할인율이 몇 퍼센트인지
     * @param couponDurationDays 쿠폰 사용 가능 기간 (일 단위)
     * @param member 발급 고객
     */
    public Coupon(DiscountType type, Integer value, int couponDurationDays, Member member, String reason) {
        this.type = type;
        this.value = value;

        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        issueDate = LocalDateTime.of(today, currentTime);

        this.expireDate = LocalDateTime.of(today.plusDays(couponDurationDays), currentTime);
        this.member = member;

        description =
                (type == DiscountType.FIX) ? String.format("{} {}원 할인 쿠폰", reason, value) : String.format("{} {}% 할인 쿠폰", reason, value);
    }

    /**
     * 테스트용 생성자: 만료된 쿠폰 테스트하기 위함.
     * @param issueDate
     * @param expireDate
     * @param member
     */
    public Coupon(LocalDateTime issueDate, LocalDateTime expireDate, Member member) {
        this.issueDate = issueDate;
        this.expireDate = expireDate;
        this.member = member;
    }

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
