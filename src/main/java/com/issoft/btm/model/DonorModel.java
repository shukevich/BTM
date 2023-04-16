package com.issoft.btm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "donor")
public class DonorModel extends PersonModel {

    @Column(name = "donorType")
    private String donorType;

    @OneToMany
    @JoinColumn(name = "donor_id")
    private List<OrderModel> orders = new ArrayList<>();

}
