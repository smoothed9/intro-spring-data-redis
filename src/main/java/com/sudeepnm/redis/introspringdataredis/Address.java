package com.sudeepnm.redis.introspringdataredis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipcode;
}
