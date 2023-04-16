package com.issoft.btm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "patient")
public class PatientModel extends PersonModel {

    @Column(name = "diagnosis")
    private String diagnosis;

    @OneToMany
    @JoinColumn(name = "patient_id")
    private List<OrderModel> orders = new ArrayList<>();

}
