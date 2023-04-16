package com.issoft.btm.dto;

import com.issoft.btm.model.dictionaries.enums.TransportationRequestStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportationRequestDTO extends AbstractDTO {
    private ZonedDateTime entryDateTime;
    private String trackingNumber;
    private String transportPartner;
    @NotNull
    private OrderDTO order;
    private TransportationRequestStatusEnum status;
    @NotNull
    private AddressDTO pickupAddress;
    @NotNull
    private AddressDTO deliverAddress;
    @NotNull
    private ContactDTO pickupContact;
    @NotNull
    private ContactDTO deliverContact;
}
