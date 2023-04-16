package com.issoft.btm.mapper;

import com.issoft.btm.dto.AddressDTO;
import com.issoft.btm.mapper.util.DictionaryMappingService;
import com.issoft.btm.model.AddressModel;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {TransportationRequestMapper.class, DictionaryMappingService.class},
        injectionStrategy = InjectionStrategy.FIELD,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public abstract class AddressMapper {

    protected DictionaryMappingService dictionaryMappingService;

    @Autowired
    public void setDictionaryMappingService(DictionaryMappingService dictionaryMappingService) {
        this.dictionaryMappingService = dictionaryMappingService;
    }
    @Mapping(target = "addressType", expression = "java(dictionaryMappingService.toAddressTypeModel(dto.getAddressType()))")
    public abstract AddressModel toAddressModel(AddressDTO dto);

    @Mapping(target = "addressType", expression = "java(dictionaryMappingService.toAddressTypeEnum(model.getAddressType()))")
    public abstract AddressDTO toAddressDTO(AddressModel model);

}
