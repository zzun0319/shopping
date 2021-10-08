package com.shopping.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String loginId;
    private String password;

    @Enumerated(EnumType.STRING)
    private Grade grade; // 회원 등급

    private Boolean saleAvailable;

    private Member(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.grade = Grade.USER;
        this.saleAvailable = false;
    }

    /**
     * 멤버 생성 메서드
     * @param name
     * @param loginId
     * @param password
     * @return 새로 생성된 멤버 리턴.
     */
    public static Member createMember(String name, String loginId, String password) {
        return new Member(name, loginId, password);
    }

    /**
     * 판매 허가 메서드
     * @param permitPassword
     */
    public void permitSale(String permitPassword){
        if(permitPassword.equals("abc1234")){
            this.saleAvailable = true;
        }
    }

}
