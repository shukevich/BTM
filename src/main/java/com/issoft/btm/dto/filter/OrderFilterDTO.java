package com.issoft.btm.dto.filter;


import com.issoft.btm.model.dictionaries.enums.OrderStatusEnum;
import com.issoft.btm.model.dictionaries.enums.OrderTypeEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderFilterDTO {
    private OrderTypeEnum orderType;
    private OrderStatusEnum orderStatus;
    private Long donorID;
    private Long patientID;
    private LocalDate entryDateBefore;
    private LocalDate entryDateAfter;
}
