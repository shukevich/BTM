package com.issoft.btm.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = {"orders"})
@Data
@ToString(exclude = {"orders"})
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO extends PersonDTO {
    @NotNull
    private String diagnosis;
    private List<OrderDTO> orders;
}
