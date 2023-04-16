package com.issoft.btm.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Setter
@Getter
@MappedSuperclass
public abstract class PersonModel extends AbstractModel {

    @Column(name = "name")
    public String name;
    @Column(name = "surname")
    public String surname;
    @Column(name = "phone")
    public String phone;

}
