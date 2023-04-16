package com.issoft.btm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PersonDTO extends AbstractDTO {

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String phone;
}
