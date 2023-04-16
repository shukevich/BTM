package com.issoft.btm.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.issoft.btm.dto.DonorDTO
import com.issoft.btm.dto.OrderDTO
import com.issoft.btm.dto.PatientDTO
import com.issoft.btm.model.dictionaries.enums.OrderStatusEnum
import com.issoft.btm.model.dictionaries.enums.OrderTypeEnum
import com.issoft.btm.util.JSONParser
import com.issoft.btm.util.TestDataGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OrderServiceSpecification extends Specification {

    @Autowired
    private TestRestTemplate restTemplate

    private static def om = new ObjectMapper()

    def "Creating and editing Order"() {

        given:
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def donorGiven = restTemplate.postForEntity("/donors", TestDataGenerator.generateDonorDTOv1(), String.class)
        def patientGiven = restTemplate.postForEntity("/patients", TestDataGenerator.generatePatientDTOv1(), String.class)
        def donorId = JSONParser.findId(donorGiven.getBody())
        def patientId = JSONParser.findId(patientGiven.getBody())
        DonorDTO donorObtained = restTemplate.getForEntity("/donors/${donorId}", DonorDTO.class).getBody()
        donorObtained.setId(donorId as Long)
        PatientDTO patientObtained = restTemplate.getForEntity("/patients/${patientId}", PatientDTO.class).getBody()
        patientObtained.setId(patientId as Long)
        def orderDTO = new OrderDTO(
                donor: donorObtained,
                patient: patientObtained,
                orderType: OrderTypeEnum.AUTO,
                orderStatus: OrderStatusEnum.OPEN
        )

        when:
        def createdOrderController = restTemplate.exchange("/order/createOrder/",
                HttpMethod.POST,
                new HttpEntity<>(om.writeValueAsString(orderDTO), headers),
                String.class)

        then:
        createdOrderController.getStatusCode() == HttpStatus.OK
        def orderActual = restTemplate.getForEntity("/orders", String.class)
        def orderId = JSONParser.findIdEmbedded(orderActual.getBody(), "orders") as Integer
        orderId == JSONParser.parseJSONResponse(createdOrderController.getBody())["id"]


    }
}
