package com.issoft.btm.full

import com.issoft.btm.dto.OrderDTO
import com.issoft.btm.dto.filter.OrderFilterDTO
import com.issoft.btm.model.dictionaries.enums.OrderStatusEnum
import com.issoft.btm.model.dictionaries.enums.OrderTypeEnum
import com.issoft.btm.service.interfaces.DonorService
import com.issoft.btm.service.interfaces.OrderService
import com.issoft.btm.service.interfaces.PatientService
import com.issoft.btm.util.TestDataGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDate
import java.util.stream.Collectors

@SpringBootTest
@ActiveProfiles("test")
class OrderDonorPatientServiceSpecification extends Specification {

    @Autowired
    OrderService orderService

    @Autowired
    DonorService donorService

    @Autowired
    PatientService patientService

    def patient1 = TestDataGenerator.generatePatientDTOv1()
    def donor1 = TestDataGenerator.generateDonorDTOv1()

    def setup() {
        donorService.createDonor(donor1)
        patientService.createPatient(patient1)
    }

    def cleanup() {
        orderService.getAllOrders(PageRequest.of(0, 10)).stream().forEach(order -> orderService.deleteOrder(order.getId()))
        donorService.getAllDonors(PageRequest.of(0, 10)).stream().forEach(donor -> donorService.deleteDonor(donor.getId()))
        patientService.getAllPatients(PageRequest.of(0, 10)).stream().forEach(patient -> patientService.deletePatient(patient.getId()))
    }


    def "Creating Order"() {
        given:
        def donor = donorService.getAllDonors(PageRequest.of(0, 10)).get(0)
        def patient = patientService.getAllPatients(PageRequest.of(0, 10)).get(0)
        def order = new OrderDTO(entryDate: LocalDate.of(2018, 05, 17),
                donor: donor,
                patient: patient,
                orderType: OrderTypeEnum.AUTO,
                orderStatus: OrderStatusEnum.OPEN
        )

        when:
        orderService.createOrder(order)

        then:
        orderService.getAllOrders(PageRequest.of(0, 10)).size() == 1
        donorService.getAllDonors(PageRequest.of(0, 10)).size() == 1
        patientService.getAllPatients(PageRequest.of(0, 10)).size() == 1

        then:
        def orderActual = orderService.getAllOrders(PageRequest.of(0, 10)).get(0)
        def donorActual = donorService.getAllDonors(PageRequest.of(0, 10)).get(0)
        def patientActual = patientService.getAllPatients(PageRequest.of(0, 10)).get(0)

        then:
        orderActual.getPatient().getId() == patientActual.getId()
        orderActual.getDonor().getId() == donorActual.getId()
        orderActual.getOrderStatus().getId() == order.getOrderStatus().getId()
        orderActual.getOrderType().getId() == order.getOrderType().getId()
        donorActual.getOrders().get(0).getId() == orderActual.getId()
        patientActual.getOrders().get(0).getId() == orderActual.getId()
    }

    def "Edit Donor, Order and Patient"() {

        given:
        def donor = donorService.getAllDonors(PageRequest.of(0, 10)).get(0)
        def patient = patientService.getAllPatients(PageRequest.of(0, 10)).get(0)
        def order = new OrderDTO(entryDate: LocalDate.of(2018, 05, 17),
                donor: donor,
                patient: patient,
                orderType: OrderTypeEnum.AUTO,
                orderStatus: OrderStatusEnum.OPEN
        )
        orderService.createOrder(order)
        def orderActual = orderService.getAllOrders(PageRequest.of(0, 10)).get(0)
        def donorActual = donorService.getAllDonors(PageRequest.of(0, 10)).get(0)
        def patientActual = patientService.getAllPatients(PageRequest.of(0, 10)).get(0)

        when:
        donorActual.setName("newDonorName")
        patientActual.setName("newPatientName")
        orderActual.setOrderStatus(OrderStatusEnum.ENDSUB)

        then:
        donorService.updateDonor(donorActual, donorActual.getId())
        patientService.updatePatient(patientActual, patientActual.getId())
        orderService.updateOrder(orderActual, orderActual.getId())

        then:
        orderService.getAllOrders(PageRequest.of(0, 10)).size() == 1
        donorService.getAllDonors(PageRequest.of(0, 10)).size() == 1
        patientService.getAllPatients(PageRequest.of(0, 10)).size() == 1
        def orderModified = orderService.getAllOrders(PageRequest.of(0, 10)).get(0)
        def donorModified = donorService.getAllDonors(PageRequest.of(0, 10)).get(0)
        def patientModified = patientService.getAllPatients(PageRequest.of(0, 10)).get(0)

        then:
        donorModified.getId() == donorActual.getId()
        patientModified.getId() == patientActual.getId()
        orderModified.getId() == orderActual.getId()
        orderModified.getPatient().getId() == patientModified.getId()
        orderModified.getDonor().getId() == donorModified.getId()
        orderModified.getOrderStatus().getId() == OrderStatusEnum.ENDSUB.getId()
        orderModified.getOrderType().getId() == order.getOrderType().getId()
        donorModified.getOrders().get(0).getId() == orderModified.getId()
        patientModified.getOrders().get(0).getId() == orderModified.getId()
        donorModified.getName() == "newDonorName"
        patientModified.getName() == "newPatientName"
    }

    def "Creating two orders and filtering"() {

        given:
        def donor1 = donorService.getAllDonors(PageRequest.of(0, 10)).get(0)
        def patient1 = patientService.getAllPatients(PageRequest.of(0, 10)).get(0)
        def order1 = new OrderDTO(entryDate: LocalDate.of(2018, 05, 17),
                donor: donor1,
                patient: patient1,
                orderType: OrderTypeEnum.AUTO,
                orderStatus: OrderStatusEnum.OPEN
        )

        orderService.createOrder(order1)
        donorService.createDonor(TestDataGenerator.generateDonorDTOv2())
        patientService.createPatient(TestDataGenerator.generatePatientDTOv2())

        def donor2 = donorService.getAllDonors(PageRequest.of(0, 10))
                .stream()
                .filter(donor -> donor.getOrders().size() == 0)
                .collect(Collectors.toList())
                .get(0)

        def patient2 = patientService.getAllPatients(PageRequest.of(0, 10))
                .stream()
                .filter(patient -> patient.getOrders().size() == 0)
                .collect(Collectors.toList())
                .get(0)

        def order2 = new OrderDTO(entryDate: LocalDate.of(2018, 05, 27),
                donor: donor2,
                patient: patient2,
                orderType: OrderTypeEnum.ALLO,
                orderStatus: OrderStatusEnum.OPEN
        )
        orderService.createOrder(order2)

        when:
        donorService.getAllDonors(PageRequest.of(0, 10)).size() == 2
        patientService.getAllPatients(PageRequest.of(0, 10)).size() == 2
        orderService.getAllOrders(PageRequest.of(0, 10)).size() == 2
        def filterDTOv1 = new OrderFilterDTO(orderType: OrderTypeEnum.AUTO)
        def filterDTOv2 = new OrderFilterDTO(orderStatus: OrderStatusEnum.OPEN)
        def filterDTOv3 = new OrderFilterDTO(
                entryDateAfter: LocalDate.of(2018, 05, 25),
                entryDateBefore: LocalDate.of(2018, 05, 30)
        )
        def filterDTOv4 = new OrderFilterDTO(donorID: 123456)
        def filteredv1 = orderService.getFilteredOrders(filterDTOv1, PageRequest.of(0, 10))
        def filteredv2 = orderService.getFilteredOrders(filterDTOv2, PageRequest.of(0, 10))
        def filteredv3 = orderService.getFilteredOrders(filterDTOv3, PageRequest.of(0, 10))
        def filteredv4 = orderService.getFilteredOrders(filterDTOv4, PageRequest.of(0, 10))

        then:
        filteredv1.size() == 1
        filteredv2.size() == 2
        filteredv3.size() == 1
        filteredv4.size() == 0

        then:
        filteredv1.get(0).getOrderType().getId() == OrderTypeEnum.AUTO.getId()
        filteredv1.get(0).getDonor().getId() == donor1.getId()

        then:
        filteredv2.stream().forEach(orderDTO -> orderDTO.getOrderStatus().getId() == OrderStatusEnum.OPEN.getId())

        then:
        filteredv3.get(0).getEntryDate() == LocalDate.of(2018, 05, 27)
        filteredv3.get(0).getDonor().getId() == donor2.getId()
        filteredv3.get(0).getPatient().getId() == patient2.getId()

    }

}
