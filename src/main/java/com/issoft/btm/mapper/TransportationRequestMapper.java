package com.issoft.btm.mapper;

import com.issoft.btm.dto.TransportationRequestDTO;
import com.issoft.btm.mapper.util.CycleAvoidingMappingContext;
import com.issoft.btm.mapper.util.DictionaryMappingService;
import com.issoft.btm.model.TransportationRequestModel;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {DictionaryMappingService.class, OrderMapper.class, DonorMapper.class, PatientMapper.class, AddressMapper.class, ContactMapper.class},
        injectionStrategy = InjectionStrategy.FIELD,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class TransportationRequestMapper {

    protected DictionaryMappingService dictionaryMappingService;

    @Autowired
    public void setDictionaryMappingService(DictionaryMappingService dictionaryMappingService) {
        this.dictionaryMappingService = dictionaryMappingService;
    }

    @Mapping(target = "status", expression = "java(dictionaryMappingService.toTransportationRequestStatusModel(dto.getStatus()))")
    public abstract TransportationRequestModel toTransportationRequestModel(TransportationRequestDTO dto, @Context CycleAvoidingMappingContext context);

    @Mapping(target = "status", expression = "java(dictionaryMappingService.toTransportationRequestStatusEnum(model.getStatus()))")
    public abstract TransportationRequestDTO toTransportationRequestDTO(TransportationRequestModel model, @Context CycleAvoidingMappingContext context);
}
