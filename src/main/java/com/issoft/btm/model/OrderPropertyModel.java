package com.issoft.btm.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "btmorder_property")
public class OrderPropertyModel extends AbstractModel {

    @Column(name = "order_property")
    private String orderProperty;

    @Column(name = "value")
    private String value;

}
