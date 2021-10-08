package com.shopping.domain;

public enum DeliveryStatus {

    COMPLETE("배송완료"), ING("배송중"), BEFORE("배송대기");

    private final String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
