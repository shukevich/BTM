package com.issoft.btm.full

import com.issoft.btm.dto.OrderDTO
import com.issoft.btm.dto.TransportationRequestDTO
import com.issoft.btm.dto.filter.TransportationRequestFilterDTO
import com.issoft.btm.model.dictionaries.enums.*
import com.issoft.btm.service.interfaces.*
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
class TransportationServiceSpecification extends Specification {

    @Autowired
    OrderService orderService

    @Autowired
    DonorService donorService

    @Autowired
    PatientService patientService

    @Autowired
    TransportationRequestService requestService

    @Autowired
    AddressService addressService

    @Autowired
    ContactService contactService

    def patient1 = TestDataGenerator.generatePatientDTOv1()
    def donor1 = TestDataGenerator.generateDonorDTOv1()
    def contactPickup = TestDataGenerator.generateContactDTOPickup()
    def contactDeliver = TestDataGenerator.generateContactDTODeliver()
    def addressPickup = TestDataGenerator.generatePickupAddress()
    def addressDeliver = TestDataGenerator.generateDeliverAddress()


    def setup() {
        donorService.createDonor(donor1)
        patientService.createPatient(patient1)
        def donor = donorService.getAllDonors(PageRequest.of(0, 10)).get(0)
        def patient = patientService.getAllPatients(PageRequest.of(0, 10)).get(0)
        def order = new OrderDTO(entryDate: LocalDate.of(2018, 05, 17),
                donor: donor,
                patient: patient,
                orderType: OrderTypeEnum.AUTO,
                orderStatus: OrderStatusEnum.OPEN
        )
        orderService.createOrder(order)
        addressService.createAddress(addressDeliver)
        addressService.createAddress(addressPickup)
        contactService.createContact(contactPickup)
        contactService.createContact(contactDeliver)
    }

    def cleanup() {
        requestService.getAllTransportationRequests(PageRequest.of(0, 10))
                .stream()
                .forEach(request -> requestService.deleteTransportationRequest(request.getId()))
        orderService.getAllOrders(PageRequest.of(0, 10)).stream().forEach(order -> orderService.deleteOrder(order.getId()))
        donorService.getAllDonors(PageRequest.of(0, 10)).stream().forEach(donor -> donorService.deleteDonor(donor.getId()))
        patientService.getAllPatients(PageRequest.of(0, 10)).stream().forEach(patient -> patientService.deletePatient(patient.getId()))
        addressService.getAllAddresses(PageRequest.of(0, 10)).stream().forEach(address -> addressService.deleteAddress(address.getId()))
        contactService.getAllContacts(PageRequest.of(0, 10)).stream().forEach(contact -> contactService.deleteContact(contact.getId()))
    }

    def "Creating Transportation Request"() {

        given:
        contactService.getAllContacts(PageRequest.of(0, 10)).size() == 2
        addressService.getAllAddresses(PageRequest.of(0, 10)).size() == 2
        orderService.getAllOrders(PageRequest.of(0, 10)).size() == 1

        def deliverContact = contactService.getAllContacts(PageRequest.of(0, 10))
                .stream()
                .filter(contact -> contact.getContactType().getId() == ContactTypeEnum.DELIVER.getId())
                .collect(Collectors.toList())
                .get(0)

        def pickupContact = contactService.getAllContacts(PageRequest.of(0, 10))
                .stream()
                .filter(contact -> contact.getContactType().getId() == ContactTypeEnum.PICKUP.getId())
                .collect(Collectors.toList())
                .get(0)

        def deliverAddress = addressService.getAllAddresses(PageRequest.of(0, 10))
                .stream()
                .filter(address -> address.getAddressType().getId() == AddressTypeEnum.DELIVER.getId())
                .collect(Collectors.toList())
                .get(0)

        def pickupAddress = addressService.getAllAddresses(PageRequest.of(0, 10))
                .stream()
                .filter(address -> address.getAddressType().getId() == AddressTypeEnum.PICKUP.getId())
                .collect(Collectors.toList())
                .get(0)

        def order = orderService.getAllOrders(PageRequest.of(0, 10)).get(0)

        when:
        def requestExpected = new TransportationRequestDTO(transportPartner: "partner1",
                order: order,
                status: TransportationRequestStatusEnum.CONFCONT,
                pickupAddress: pickupAddress,
                deliverAddress: deliverAddress,
                pickupContact: pickupContact,
                deliverContact: deliverContact)
        requestService.createTransportationRequest(requestExpected)

        then:
        requestService.getAllTransportationRequests(PageRequest.of(0, 10)).size() == 1
        def requestGiven = requestService.getAllTransportationRequests(PageRequest.of(0, 10)).get(0)

        then:
        requestGiven.getOrder().getId() == order.getId()
        requestGiven.getStatus().getId() == TransportationRequestStatusEnum.CONFCONT.getId()
        requestGiven.getPickupAddress().getId() == pickupAddress.getId()
        requestGiven.getDeliverAddress().getId() == deliverAddress.getId()
        requestGiven.getPickupContact().getId() == pickupContact.getId()
        requestGiven.getDeliverContact().getId() == deliverContact.getId()

    }

    def "Edit Transportation Request and Order"() {

        given:
        contactService.getAllContacts(PageRequest.of(0, 10)).size() == 2
        addressService.getAllAddresses(PageRequest.of(0, 10)).size() == 2
        orderService.getAllOrders(PageRequest.of(0, 10)).size() == 1

        def deliverContact = contactService.getAllContacts(PageRequest.of(0, 10))
                .stream()
                .filter(contact -> contact.getContactType().getId() == ContactTypeEnum.DELIVER.getId())
                .collect(Collectors.toList())
                .get(0)

        def pickupContact = contactService.getAllContacts(PageRequest.of(0, 10))
                .stream()
                .filter(contact -> contact.getContactType().getId() == ContactTypeEnum.PICKUP.getId())
                .collect(Collectors.toList())
                .get(0)

        def deliverAddress = addressService.getAllAddresses(PageRequest.of(0, 10))
                .stream()
                .filter(address -> address.getAddressType().getId() == AddressTypeEnum.DELIVER.getId())
                .collect(Collectors.toList())
                .get(0)

        def pickupAddress = addressService.getAllAddresses(PageRequest.of(0, 10))
                .stream()
                .filter(address -> address.getAddressType().getId() == AddressTypeEnum.PICKUP.getId())
                .collect(Collectors.toList())
                .get(0)

        def order = orderService.getAllOrders(PageRequest.of(0, 10)).get(0)

        def requestExpected = new TransportationRequestDTO(transportPartner: "partner1",
                order: order,
                status: TransportationRequestStatusEnum.CONFCONT,
                pickupAddress: pickupAddress,
                deliverAddress: deliverAddress,
                pickupContact: pickupContact,
                deliverContact: deliverContact)
        requestService.createTransportationRequest(requestExpected)

        when:
        requestService.getAllTransportationRequests(PageRequest.of(0, 10)).size() == 1
        def requestGiven = requestService.getAllTransportationRequests(PageRequest.of(0, 10)).get(0)
        requestGiven.getStatus().getId() == TransportationRequestStatusEnum.CONFCONT.getId()
        requestGiven.setStatus(TransportationRequestStatusEnum.CONFPUP)
        requestService.updateTransportationRequest(requestGiven)

        then:
        requestService.getAllTransportationRequests(PageRequest.of(0, 10)).size() == 1
        def requestActual = requestService.getAllTransportationRequests(PageRequest.of(0, 10)).get(0)
        requestActual.getId() == requestGiven.getId()
        requestActual.getOrder().getId() == order.getId()
        requestActual.getStatus().getId() == TransportationRequestStatusEnum.CONFPUP.getId()
        requestActual.getOrder().getOrderStatus().getId() == OrderStatusEnum.OPEN.getId()
    }

    def "Finding Requests for Order"() {
        given:
        contactService.getAllContacts(PageRequest.of(0, 10)).size() == 2
        addressService.getAllAddresses(PageRequest.of(0, 10)).size() == 2
        orderService.getAllOrders(PageRequest.of(0, 10)).size() == 1

        def deliverContact = contactService.getAllContacts(PageRequest.of(0, 10))
                .stream()
                .filter(contact -> contact.getContactType().getId() == ContactTypeEnum.DELIVER.getId())
                .collect(Collectors.toList())
                .get(0)

        def pickupContact = contactService.getAllContacts(PageRequest.of(0, 10))
                .stream()
                .filter(contact -> contact.getContactType().getId() == ContactTypeEnum.PICKUP.getId())
                .collect(Collectors.toList())
                .get(0)

        def deliverAddress = addressService.getAllAddresses(PageRequest.of(0, 10))
                .stream()
                .filter(address -> address.getAddressType().getId() == AddressTypeEnum.DELIVER.getId())
                .collect(Collectors.toList())
                .get(0)

        def pickupAddress = addressService.getAllAddresses(PageRequest.of(0, 10))
                .stream()
                .filter(address -> address.getAddressType().getId() == AddressTypeEnum.PICKUP.getId())
                .collect(Collectors.toList())
                .get(0)

        def order = orderService.getAllOrders(PageRequest.of(0, 10)).get(0)

        def requestExpected = new TransportationRequestDTO(transportPartner: "partner1",
                order: order,
                status: TransportationRequestStatusEnum.CONFCONT,
                pickupAddress: pickupAddress,
                deliverAddress: deliverAddress,
                pickupContact: pickupContact,
                deliverContact: deliverContact)
        requestService.createTransportationRequest(requestExpected)

        when:
        def requests = requestService.getAllTransportationRequestsForOrder(order.getId(), PageRequest.of(0, 10))

        then:
        requests.size() == 1
        requests.get(0).getOrder().getId() == order.getId()
    }

    def "Filtering Requests"() {
        given:
        contactService.getAllContacts(PageRequest.of(0, 10)).size() == 2
        addressService.getAllAddresses(PageRequest.of(0, 10)).size() == 2
        orderService.getAllOrders(PageRequest.of(0, 10)).size() == 1

        def deliverContact = contactService.getAllContacts(PageRequest.of(0, 10))
                .stream()
                .filter(contact -> contact.getContactType().getId() == ContactTypeEnum.DELIVER.getId())
                .collect(Collectors.toList())
                .get(0)

        def pickupContact = contactService.getAllContacts(PageRequest.of(0, 10))
                .stream()
                .filter(contact -> contact.getContactType().getId() == ContactTypeEnum.PICKUP.getId())
                .collect(Collectors.toList())
                .get(0)

        def deliverAddress = addressService.getAllAddresses(PageRequest.of(0, 10))
                .stream()
                .filter(address -> address.getAddressType().getId() == AddressTypeEnum.DELIVER.getId())
                .collect(Collectors.toList())
                .get(0)

        def pickupAddress = addressService.getAllAddresses(PageRequest.of(0, 10))
                .stream()
                .filter(address -> address.getAddressType().getId() == AddressTypeEnum.PICKUP.getId())
                .collect(Collectors.toList())
                .get(0)

        def order = orderService.getAllOrders(PageRequest.of(0, 10)).get(0)

        def requestExpected = new TransportationRequestDTO(transportPartner: "partner1",
                order: order,
                status: TransportationRequestStatusEnum.CONFCONT,
                pickupAddress: pickupAddress,
                deliverAddress: deliverAddress,
                pickupContact: pickupContact,
                deliverContact: deliverContact)
        requestService.createTransportationRequest(requestExpected)

        when:
        def filterv1 = new TransportationRequestFilterDTO(transportationStatus: TransportationRequestStatusEnum.CONFCONT)
        def filterv2 = new TransportationRequestFilterDTO(deliveryCountry: "USA")
        def filterv3 = new TransportationRequestFilterDTO(transportPartner: "unknown")

        then:
        def filteredv1 = requestService.getFilteredRequests(filterv1, PageRequest.of(0, 10))
        def filteredv2 = requestService.getFilteredRequests(filterv2, PageRequest.of(0, 10))
        def filteredv3 = requestService.getFilteredRequests(filterv3, PageRequest.of(0, 10))

        then:
        filteredv1.size() == 1
        filteredv2.size() == 1
        filteredv3.size() == 0
        filteredv1.get(0).getStatus().getId() == TransportationRequestStatusEnum.CONFCONT.getId()
        filteredv2.get(0).getDeliverAddress().getCountry() == "USA"

    }


}
