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
@AttributeOverride(name = "id", column = @Column(name = "transportation_request_status_id"))
@Table(name = "transportation_request_status")
public class TransportationRequestStatusModel extends BaseDictionary {
}

