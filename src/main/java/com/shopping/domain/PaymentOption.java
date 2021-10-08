package com.shopping.domain;

public enum PaymentOption {

    CREDIT_CARD("신용카드"), TRANSFER("송금");

    private final String desc;

    PaymentOption(String desc) {
        this.desc = desc;
    }
}
