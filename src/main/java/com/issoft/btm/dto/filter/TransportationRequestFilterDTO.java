package com.issoft.btm.dto.filter;

import com.issoft.btm.model.dictionaries.enums.TransportationRequestStatusEnum;
import lombok.Data;

@Data
public class TransportationRequestFilterDTO {
    private TransportationRequestStatusEnum transportationStatus;
    private String transportPartner;
    private String pickupCountry;
    private String deliveryCountry;
}
