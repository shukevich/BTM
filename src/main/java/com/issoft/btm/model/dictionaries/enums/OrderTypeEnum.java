package com.issoft.btm.model.dictionaries.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderTypeEnum {
    AUTO(1, "AUTO"),
    ALLO(2, "ALLO");

    private final Integer id;
    private final String name;

}
