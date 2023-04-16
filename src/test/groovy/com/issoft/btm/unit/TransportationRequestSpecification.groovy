package com.issoft.btm.unit


import com.issoft.btm.dto.OrderDTO
import com.issoft.btm.dto.TransportationRequestDTO
import com.issoft.btm.mapper.TransportationRequestMapper
import com.issoft.btm.mapper.util.CycleAvoidingMappingContext
import com.issoft.btm.model.OrderModel
import com.issoft.btm.model.TransportationRequestModel
import com.issoft.btm.repository.TransportationRequestRepository
import com.issoft.btm.service.TransportationRequestServiceImpl
import spock.lang.Specification

import java.time.LocalDate

class TransportationRequestSpecification extends Specification {

    def transportationRequestRepository = Mock(TransportationRequestRepository)
    def transportationRequestMapper = Mock(TransportationRequestMapper)
    def context = Mock(CycleAvoidingMappingContext)
    def transportationRequestService = new TransportationRequestServiceImpl(
            transportationRequestRepository,
            transportationRequestMapper,
            context)

    def orderDTO = new OrderDTO(
            id: 1,
            entryDate: LocalDate.of(2020, 05, 10)
    )

    def orderModel = new OrderModel(
            id: 1,
            entryDate: LocalDate.of(2020, 05, 10)
    )

    def transportationRequestDTO = new TransportationRequestDTO(
            transportPartner: "123partner",
            order: orderDTO
    )

    def transportationRequestDTOwithID = new TransportationRequestDTO(id: 1L,
            transportPartner: "123partner",
            order: orderDTO
    )

    def transportationModel = new TransportationRequestModel(
            transportPartner: "123partner",
            order: orderModel
    )

    def transportationModelWithID = new TransportationRequestModel(id: 1,
            transportPartner: "123partner",
            order: orderModel
    )

    def "Transportation Request Service returns RequestDTO by requested RequestID"() {

        when: "requesting Order by ID"
        transportationRequestDTOwithID = transportationRequestService.createTransportationRequest(transportationRequestDTO)

        then: "DTO should be mapped to model"
        1 * transportationRequestMapper.toTransportationRequestModel(transportationRequestDTO, context) >> transportationModel

        then: "Request repository should create and return model"
        1 * transportationRequestRepository.save(transportationModel) >> transportationModelWithID

        then: "Mapping Model to DTO"
        1 * transportationRequestMapper.toTransportationRequestDTO(transportationModelWithID, context) >> transportationRequestDTOwithID

        then: "comparing sample DTO and obtained one"
        transportationRequestDTOwithID.getPickupAddress() == transportationRequestDTO.getPickupAddress()
        transportationRequestDTOwithID.getPickupContact() == transportationRequestDTO.getPickupContact()
        transportationRequestDTOwithID.getOrder() == transportationRequestDTO.getOrder()
        transportationRequestDTO.getDeliverContact() == transportationRequestDTO.getDeliverContact()
        transportationRequestDTO.getDeliverAddress() == transportationRequestDTO.getDeliverAddress()

        then: "no calls for Service"
        0 * transportationRequestService.createTransportationRequest(transportationRequestDTO)
    }
}
