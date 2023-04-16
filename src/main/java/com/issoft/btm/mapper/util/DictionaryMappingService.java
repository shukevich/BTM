package com.issoft.btm.mapper.util;

import com.issoft.btm.model.dictionaries.*;
import com.issoft.btm.model.dictionaries.enums.*;
import com.issoft.btm.repository.dictionary.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DictionaryMappingService {

    private final TransportationRequestStatusRepository requestStatusRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderTypeRepository orderTypeRepository;
    private final AddressTypeRepository addressTypeRepository;
    private final ContactTypeRepository contactTypeRepository;


    public TransportationRequestStatusEnum toTransportationRequestStatusEnum(TransportationRequestStatusModel requestModel) {
        return Arrays.stream(TransportationRequestStatusEnum.values())
                .filter(requestStatusEnum -> requestStatusEnum.getId().equals(requestModel.getId()))
                .reduce((a, b) -> null).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public TransportationRequestStatusModel toTransportationRequestStatusModel(TransportationRequestStatusEnum requestStatusEnum) {
        return requestStatusRepository.findById(requestStatusEnum.getId()).orElseThrow(IllegalArgumentException::new);
    }

    public OrderStatusEnum toOrderStatusEnum(OrderStatusModel orderStatusModel) {
        return Arrays.stream(OrderStatusEnum.values())
                .filter(orderStatusEnum -> orderStatusEnum.getId().equals(orderStatusModel.getId()))
                .reduce((a, b) -> null).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public OrderStatusModel toOrderStatusModel(OrderStatusEnum orderStatusEnum) {
        return orderStatusRepository.findById(orderStatusEnum.getId()).orElseThrow(IllegalArgumentException::new);
    }

    public OrderTypeEnum toOrderTypeEnum(OrderTypeModel orderTypeModel) {
        return Arrays.stream(OrderTypeEnum.values())
                .filter(orderTypeEnum -> orderTypeEnum.getId().equals(orderTypeModel.getId()))
                .reduce((a, b) -> null).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public OrderTypeModel toOrderTypeModel(OrderTypeEnum orderTypeEnum) {
        return orderTypeRepository.findById(orderTypeEnum.getId()).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public AddressTypeModel toAddressTypeModel(AddressTypeEnum addressTypeEnum) {
        return addressTypeRepository.findById(addressTypeEnum.getId()).orElseThrow(IllegalArgumentException::new);
    }

    public AddressTypeEnum toAddressTypeEnum(AddressTypeModel addressTypeModel) {
        return Arrays.stream(AddressTypeEnum.values())
                .filter(addressTypeEnum -> addressTypeEnum.getId().equals(addressTypeModel.getId()))
                .reduce((a, b) -> null).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public ContactTypeModel toContactTypeModel(ContactTypeEnum contactTypeEnum) {
        return contactTypeRepository.findById(contactTypeEnum.getId()).orElseThrow(IllegalArgumentException::new);
    }

    public ContactTypeEnum toContactTypeEnum(ContactTypeModel contactTypeModel) {
        return Arrays.stream(ContactTypeEnum.values())
                .filter(contactTypeEnum -> contactTypeEnum.getId().equals(contactTypeModel.getId()))
                .reduce((a, b) -> null).orElseThrow(IllegalArgumentException::new);
    }
}
