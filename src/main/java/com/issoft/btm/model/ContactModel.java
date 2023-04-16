package com.issoft.btm.model;

import com.issoft.btm.model.dictionaries.ContactTypeModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "contact")
public class ContactModel extends PersonModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_type_id")
    private ContactTypeModel contactType;

}
