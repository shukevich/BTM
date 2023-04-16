package com.issoft.btm.dto;

import com.issoft.btm.model.dictionaries.enums.OrderStatusEnum;
import com.issoft.btm.model.dictionaries.enums.OrderTypeEnum;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = {"orderProperties", "requests"})
@Data
@ToString(exclude = {"orderProperties", "requests"})
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends AbstractDTO {
    private LocalDate entryDate;
    @NotNull
    private DonorDTO donor;
    @NotNull
    private PatientDTO patient;
    @NotNull
    private OrderStatusEnum orderStatus;
    @NotNull
    private OrderTypeEnum orderType;
    private List<OrderPropertyDTO> orderProperties;
    private List<TransportationRequestDTO> requests;

}
