package com.issoft.btm.mapper;

import com.issoft.btm.dto.PatientDTO;
import com.issoft.btm.mapper.util.CycleAvoidingMappingContext;
import com.issoft.btm.model.PatientModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {OrderMapper.class, TransportationRequestMapper.class},
        injectionStrategy = InjectionStrategy.FIELD,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class PatientMapper {

    public abstract PatientModel toPatientModel(PatientDTO patientDTO, @Context CycleAvoidingMappingContext context);

    public abstract PatientDTO toPatientDTO(PatientModel patientModel, @Context CycleAvoidingMappingContext context);
}
