package com.issoft.btm.model;

import com.issoft.btm.model.dictionaries.OrderStatusModel;
import com.issoft.btm.model.dictionaries.OrderTypeModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "btmorder")
public class OrderModel extends AbstractModel {

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_id")
    private OrderStatusModel orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id")
    private DonorModel donor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private PatientModel patient;

    @OneToMany
    @JoinColumn(name = "btmorder_id")
    private List<TransportationRequestModel> requests = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_type_id")
    private OrderTypeModel orderType;

    @OneToMany
    @JoinColumn(name = "btmorder_id")
    private List<OrderPropertyModel> orderProperties = new ArrayList<>();
}
