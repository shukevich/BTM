package com.issoft.btm.dto;

import com.issoft.btm.model.dictionaries.enums.ContactTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class ContactDTO extends PersonDTO {
    @NotNull
    private ContactTypeEnum contactType;
}
