package com.issoft.btm.model.dictionaries.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransportationRequestStatusEnum {

    REVREQ(1, "Review Request"),
    PLORDER(2, "Place Order"),
    SENDCONT(3, "Send Container"),
    CONFCONT(4, "Confirm Container Delivery"),
    CONFPUP(5, "Confirm Product Pickup"),
    CONFPDEL(6, "Confirm Product Delivery"),
    CONTRETURN(7, "Confirm Container Return"),
    CLOSED(8, "Close Request"),
    CANCELED(9, "Canceled");

    private final Integer id;
    private final String name;

}
