package com.issoft.btm.model;

import com.issoft.btm.model.dictionaries.TransportationRequestStatusModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "transportation_request")
public class TransportationRequestModel extends AbstractModel {

    @Column(name = "pickup_datetime", updatable = false)
    private ZonedDateTime entryDateTime;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "transport_partner")
    private String transportPartner;

    @ManyToOne
    @JoinColumn(name = "transportation_request_status_id", nullable = false)
    private TransportationRequestStatusModel status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "btmorder_id")
    private OrderModel order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_contact_id")
    private ContactModel pickupContact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliver_contact_id")
    private ContactModel deliverContact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_address_id")
    private AddressModel pickupAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliver_address_id")
    private AddressModel deliverAddress;

}
