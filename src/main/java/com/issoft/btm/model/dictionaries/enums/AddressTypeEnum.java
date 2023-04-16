package com.issoft.btm.model.dictionaries.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AddressTypeEnum {
    PICKUP(1, "Pickup address"),
    DELIVER(2, "Delivery address");

    private final Integer id;
    private final String name;
}
