package com.issoft.btm.dto;

import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;

@Data
@Valid
public abstract class AbstractDTO implements Serializable {
    private Long id;
}
