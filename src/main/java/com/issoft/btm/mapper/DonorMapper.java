package com.issoft.btm.mapper;

import com.issoft.btm.dto.DonorDTO;
import com.issoft.btm.mapper.util.CycleAvoidingMappingContext;
import com.issoft.btm.model.DonorModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {OrderMapper.class, TransportationRequestMapper.class},
        injectionStrategy = InjectionStrategy.FIELD,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class DonorMapper {

    public abstract DonorModel toDonorModel(DonorDTO donorDTO, @Context CycleAvoidingMappingContext context);

    public abstract DonorDTO toDonorDTO(DonorModel donorModel, @Context CycleAvoidingMappingContext context);
}
