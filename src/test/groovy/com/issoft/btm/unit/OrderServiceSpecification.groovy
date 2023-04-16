package com.issoft.btm.unit

import com.issoft.btm.dto.DonorDTO
import com.issoft.btm.dto.OrderDTO
import com.issoft.btm.dto.PatientDTO
import com.issoft.btm.mapper.OrderMapper
import com.issoft.btm.mapper.util.CycleAvoidingMappingContext
import com.issoft.btm.model.DonorModel
import com.issoft.btm.model.OrderModel
import com.issoft.btm.model.PatientModel
import com.issoft.btm.model.dictionaries.OrderStatusModel
import com.issoft.btm.model.dictionaries.OrderTypeModel
import com.issoft.btm.model.dictionaries.enums.OrderStatusEnum
import com.issoft.btm.model.dictionaries.enums.OrderTypeEnum
import com.issoft.btm.repository.OrderRepository
import com.issoft.btm.service.OrderServiceImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import java.time.LocalDate

class OrderServiceSpecification extends Specification {

    def orderRepository = Mock(OrderRepository)
    def orderMapper = Mock(OrderMapper)
    def context = Mock(CycleAvoidingMappingContext)
    def orderService = new OrderServiceImpl(orderRepository, orderMapper, context)

    def donorDTO = new DonorDTO(id: 1, name: "Vasiliy")
    def donorDTO2 = new DonorDTO(id: 2, name: "Ivan")
    def patientDTO = new PatientDTO(id: 1, name: "Petr")
    def patientDTO2 = new PatientDTO(id: 2, name: "Sergey")
    def orderDTO = new OrderDTO(
            id: 1,
            entryDate: LocalDate.of(2020, 05, 10),
            donor: donorDTO,
            patient: patientDTO,
            orderType: OrderTypeEnum.ALLO,
            orderStatus: OrderStatusEnum.OPEN)
    def orderDTO2 = new OrderDTO(
            id: 2,
            entryDate: LocalDate.of(2020, 07, 11),
            donor: donorDTO2,
            patient: patientDTO2,
            orderType: OrderTypeEnum.ALLO,
            orderStatus: OrderStatusEnum.OPEN)
    def patientModel = new PatientModel(id: 1, name: "Petr")
    def patientModel2 = new PatientModel(id: 1, name: "Sergey")
    def donorModel = new DonorModel(id: 1, name: "Vasiliy")
    def donorModel2 = new DonorModel(id: 2, name: "Ivan")
    def orderStatusModel = new OrderStatusModel(id: 1, name: "Open")
    def orderTypeModel = new OrderTypeModel(id: 1, name: "ALLO")
    def orderModel = new OrderModel(
            id: 1,
            orderType: orderTypeModel,
            entryDate: LocalDate.of(2020, 05, 10),
            orderStatus: orderStatusModel,
            donor: donorModel,
            patient: patientModel)

    def orderModel2 = new OrderModel(
            id: 2,
            orderType: orderTypeModel,
            entryDate: LocalDate.of(2020, 07, 11),
            orderStatus: orderStatusModel,
            donor: donorModel2,
            patient: patientModel2)

    def setuo() {
        donorDTO.setOrders(List.of(orderDTO))
        patientDTO.setOrders(List.of(orderDTO))
        donorDTO2.setOrders(List.of(orderDTO2))
        patientDTO2.setOrders(List.of(orderDTO2))
        donorModel.setOrders(List.of(orderModel))
        patientModel.setOrders(List.of(orderModel))
        patientModel2.setOrders(List.of(orderModel2))
        donorModel2.setOrders(List.of(orderModel2))
    }

    def "Order Service returns OrderDTO by requested OrderID"() {

        when: "requesting Order by ID"
        def obtainedDTO = orderService.getOrder(1L)

        then: "Order repository that always returns this order"
        1 * orderRepository.findById(1L) >> Optional.of(orderModel)
        1 * orderMapper.toOrderDTO(orderModel, context) >> orderDTO

        then: "comparing sample Donor and obtained one"
        orderDTO == obtainedDTO

        then: "no calls for Service"
        0 * orderService.getOrder(1L)
    }

    def "Order Service updates Order based on Donor, Order and Patient DTO`s with ID`s"() {

        when: "updating Order by ID"
        def obtainedDTO = orderService.updateOrder(orderDTO, 1L)

        then: "Order repository that always saves this order and returns it"
        1 * orderMapper.toOrderModel(orderDTO, context) >> orderModel
        1 * orderRepository.save(orderModel) >> orderModel
        1 * orderMapper.toOrderDTO(orderModel, context) >> orderDTO

        then: "Checking equality"
        obtainedDTO == orderDTO

        then: "No calls for service"
        0 * orderService.updateOrder(orderDTO, 1L)
    }

    def "Order Service returns all Orders"() {
        given: "2nd DTO and Model"
        List<OrderDTO> expected = List.of(orderDTO, orderDTO2)
        Page<OrderModel> page = new PageImpl<>(List.of(orderModel, orderModel2))

        when: "Requesting All Orders"
        List<OrderDTO> actual = orderService.getAllOrders(PageRequest.of(0, 10))

        then: "OrderRepository returns list of orders"
        1 * orderRepository.findAll(PageRequest.of(0, 10)) >> page

        then: "Mapping Entities to DTO"
        1 * orderMapper.toOrderDTO(orderModel, context) >> orderDTO
        1 * orderMapper.toOrderDTO(orderModel2, context) >> orderDTO2

        then: "Comparing Lists"
        actual == expected
    }


}
