package com.issoft.btm.dto;

import com.issoft.btm.model.dictionaries.enums.AddressTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddressDTO extends AbstractDTO {
    @NotNull
    private AddressTypeEnum addressType;
    @NotNull
    private String address;
    @NotNull
    private String country;
    private String state;
    @NotNull
    private String city;
}
