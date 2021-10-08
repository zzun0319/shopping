package com.shopping.domain;

import lombok.AllArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;

    private DeliveryStatus status;
}
