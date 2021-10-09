package com.shopping.domain.valuetypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public String getFullAddress() {
        return String.format("{} {} {}", city, street, zipcode);
    }
}
