package com.issoft.btm.mapper;

import com.issoft.btm.dto.ContactDTO;
import com.issoft.btm.mapper.util.DictionaryMappingService;
import com.issoft.btm.model.ContactModel;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        uses = {TransportationRequestMapper.class, DictionaryMappingService.class},
        injectionStrategy = InjectionStrategy.FIELD,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public abstract class ContactMapper {

    protected DictionaryMappingService dictionaryMappingService;

    @Autowired
    public void setDictionaryMappingService(DictionaryMappingService dictionaryMappingService) {
        this.dictionaryMappingService = dictionaryMappingService;
    }

    @Mapping(target = "contactType", expression = "java(dictionaryMappingService.toContactTypeModel(dto.getContactType()))")
    public abstract ContactModel toContactModel(ContactDTO dto);

    @Mapping(target = "contactType", expression = "java(dictionaryMappingService.toContactTypeEnum(model.getContactType()))")
    public abstract ContactDTO toContactDTO(ContactModel model);


}
