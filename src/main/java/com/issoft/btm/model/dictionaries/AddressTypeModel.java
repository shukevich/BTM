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
@AttributeOverride(name = "id", column = @Column(name = "address_type_id"))
@Table(name = "address_type")
public class AddressTypeModel extends BaseDictionary {

}
