package com.issoft.btm.model.dictionaries;

import com.issoft.btm.model.dictionaries.base.BaseDictionary;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "order_type_id"))
@Table(name = "order_type")
public class OrderTypeModel extends BaseDictionary {

}
