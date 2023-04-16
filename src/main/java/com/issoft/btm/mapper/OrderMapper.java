package com.issoft.btm.mapper;

import com.issoft.btm.dto.OrderDTO;
import com.issoft.btm.mapper.util.CycleAvoidingMappingContext;
import com.issoft.btm.mapper.util.DictionaryMappingService;
import com.issoft.btm.model.OrderModel;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {DictionaryMappingService.class, TransportationRequestMapper.class, PatientMapper.class, DonorMapper.class},
        injectionStrategy = InjectionStrategy.FIELD,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class OrderMapper {

    protected DictionaryMappingService dictionaryMappingService;

    @Autowired
    public void setDictionaryMappingService(DictionaryMappingService dictionaryMappingService) {
        this.dictionaryMappingService = dictionaryMappingService;
    }

    @Mappings({
            @Mapping(target = "orderStatus", expression = "java(dictionaryMappingService.toOrderStatusModel(orderDTO.getOrderStatus()))"),
            @Mapping(target = "orderType", expression = "java(dictionaryMappingService.toOrderTypeModel(orderDTO.getOrderType()))")
    })
    public abstract OrderModel toOrderModel(OrderDTO orderDTO, @Context CycleAvoidingMappingContext context);

    @Mappings({
            @Mapping(target = "orderStatus", expression = "java(dictionaryMappingService.toOrderStatusEnum(orderModel.getOrderStatus()))"),
            @Mapping(target = "orderType", expression = "java(dictionaryMappingService.toOrderTypeEnum(orderModel.getOrderType()))")
    })
    public abstract OrderDTO toOrderDTO(OrderModel orderModel, @Context CycleAvoidingMappingContext context);
}
