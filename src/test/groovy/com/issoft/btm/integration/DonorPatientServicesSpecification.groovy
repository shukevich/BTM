package com.issoft.btm.integration

import com.fasterxml.jackson.databind.ObjectMapper
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
class DonorPatientServicesSpecification extends Specification {

    @Autowired
    private TestRestTemplate restTemplate

    private static def om = new ObjectMapper()


    def "Reading existing Donor and Patient, creating and editing them"() {

        given:
        def donorGiven = restTemplate.postForEntity("/donors", TestDataGenerator.generateDonorModel(), String.class)
        def patientGiven = restTemplate.postForEntity("/patients", TestDataGenerator.generatePatientModel(), String.class)
        def donorId = JSONParser.findId(donorGiven.getBody())
        def patientId = JSONParser.findId(patientGiven.getBody())

        and:
        def donor = restTemplate.getForEntity("/donors/${donorId}", String.class)
        def patient = restTemplate.getForEntity("/patients/${patientId}", String.class)
        donor.getStatusCode() == HttpStatus.OK
        patient.getStatusCode() == HttpStatus.OK

        when:
        def donorActual = restTemplate.getForEntity("/donor/getDonor/${donorId}", String.class)
        def patientActual = restTemplate.getForEntity("/patient/getPatient/${patientId}", String.class)

        then:
        donorActual.getStatusCode() == HttpStatus.OK
        JSONParser.parseJSONResponse(donorActual.getBody())["donorType"] == JSONParser.readJSONFromFileResponse("DonorCreatedResponse")["donorType"]
        JSONParser.parseJSONResponse(donorActual.getBody())["id"] == donorId as Integer
        patientActual.getStatusCode() == HttpStatus.OK
        JSONParser.parseJSONResponse(patientActual.getBody())["diagnosis"] == JSONParser.readJSONFromFileResponse("PatientCreatedResponse")["diagnosis"]
        JSONParser.parseJSONResponse(patientActual.getBody())["id"] == patientId as Integer

        then:
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def updateDonor = new HttpEntity<>(om.writeValueAsString(TestDataGenerator.generateDonorDTOv1()), headers)
        def updatePatient = new HttpEntity<>(om.writeValueAsString(TestDataGenerator.generatePatientDTOv1()), headers)
        def updatedDonorController = restTemplate.exchange("/donor/updateDonor/${donorId}", HttpMethod.PUT, updateDonor, String.class)
        def updatedPatientController = restTemplate.exchange("/patient/updatePatient/${patientId}", HttpMethod.PUT, updatePatient, String.class)

        then:
        def donorUpdated = restTemplate.getForEntity("/donors/${donorId}", String.class)
        def patientUpdated = restTemplate.getForEntity("/patients/${patientId}", String.class)

        then:
        updatedDonorController.getStatusCode() == HttpStatus.OK
        updatedPatientController.getStatusCode() == HttpStatus.OK
        donorUpdated.getStatusCode() == HttpStatus.OK
        patientUpdated.getStatusCode() == HttpStatus.OK
        JSONParser.findId(donorUpdated.getBody()) == donorId
        JSONParser.findId(patientUpdated.getBody()) == patientId
        JSONParser.parseJSONResponse(donorUpdated.getBody())["phone"] == JSONParser.readJSONFromFileResponse("DonorUpdatedResponse")["phone"]
        JSONParser.parseJSONResponse(patientUpdated.getBody())["phone"] == JSONParser.readJSONFromFileResponse("PatientUpdatedResponse")["phone"]

        and:
        def anotherDonor = new HttpEntity<>(om.writeValueAsString(TestDataGenerator.generateDonorDTOv2()), headers)
        def anotherPatient = new HttpEntity<>(om.writeValueAsString(TestDataGenerator.generatePatientDTOv2()), headers)
        def anotherDonorController = restTemplate.exchange("/donor/createDonor/", HttpMethod.POST, anotherDonor, String.class)
        def anotherPatientController = restTemplate.exchange("/patient/createPatient/", HttpMethod.POST, anotherPatient, String.class)

        then:
        anotherPatientController.getStatusCode() == HttpStatus.OK
        anotherDonorController.getStatusCode() == HttpStatus.OK
        def anotherDonorId = JSONParser.parseJSONResponse(anotherDonorController.getBody())["id"]
        def anotherPatientId = JSONParser.parseJSONResponse(anotherPatientController.getBody())["id"]
        anotherDonorId != donorId
        anotherPatientId != patientId

        then:
        def donorAnother = restTemplate.getForEntity("/donors/${anotherDonorId}", String.class)
        def patientAnother = restTemplate.getForEntity("/patients/${anotherPatientId}", String.class)
        JSONParser.parseJSONResponse(donorAnother.getBody())["name"] == JSONParser.readJSONFromFileResponse("Donorv2CreatedResponse")["name"]
        JSONParser.parseJSONResponse(patientAnother.getBody())["name"] == JSONParser.readJSONFromFileResponse("Patientv2CreatedResponse")["name"]

        cleanup:
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders())
        restTemplate.exchange("/donor/deleteDonor/${donorId}", HttpMethod.DELETE, entity, String.class)
        restTemplate.exchange("/donor/deleteDonor/${anotherDonorId}", HttpMethod.DELETE, entity, String.class)
        restTemplate.exchange("/patient/deletePatient/${patientId}", HttpMethod.DELETE, entity, String.class)
        restTemplate.exchange("/patient/deletePatient/${anotherPatientId}", HttpMethod.DELETE, entity, String.class)
        def donorNone = restTemplate.getForEntity("/donors", String.class)
        def patientNone = restTemplate.getForEntity("/patients", String.class)
        JSONParser.findId(donorNone.getBody()) == "donors"
        JSONParser.findId(patientNone.getBody()) == "patients"
    }
}
