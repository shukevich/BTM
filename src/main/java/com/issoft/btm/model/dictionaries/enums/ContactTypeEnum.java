package com.issoft.btm.model.dictionaries.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContactTypeEnum {
    PICKUP(1, "Contact for pickup"),
    DELIVER(2, "Deliver contact");

    private final Integer id;
    private final String name;
}
