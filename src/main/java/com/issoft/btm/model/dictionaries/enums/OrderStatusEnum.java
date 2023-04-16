package com.issoft.btm.model.dictionaries.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatusEnum {
    OPEN(1, "Open"),
    CLOSED(2, "Closed"),
    ENDSUB(3, "Unknown");

    private final Integer id;
    private final String name;
}
