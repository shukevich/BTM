package com.issoft.btm.model.dictionaries.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseDictionary {

    @Id
    @Column(name = "id")
    protected Integer id;

    @Column(name = "name")
    protected String name;

}
