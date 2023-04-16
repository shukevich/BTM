package com.issoft.btm.unit


import com.issoft.btm.dto.PatientDTO
import com.issoft.btm.mapper.PatientMapper
import com.issoft.btm.mapper.util.CycleAvoidingMappingContext
import com.issoft.btm.model.PatientModel
import com.issoft.btm.repository.PatientRepository
import com.issoft.btm.service.PatientServiceImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class PatientServiceSpecification extends Specification {

    def patientRepository = Mock(PatientRepository)
    def patientMapper = Mock(PatientMapper)
    def context = Mock(CycleAvoidingMappingContext)
    def patientService = new PatientServiceImpl(patientRepository, patientMapper, context)
    def patientDTO = new PatientDTO(id: 1L, name: "Ivan")
    def patientModel = new PatientModel(id: 1L, name: "Ivan")

    def "Patient Service returns PatientDTO by requested PatientID"() {

        given: "patient with sample field values"

        and: "Patient repository that always returns this patient"
        1 * patientRepository.findById(1L) >> Optional.of(patientModel)
        1 * patientMapper.toPatientDTO(patientModel, context) >> patientDTO

        when: "requesting Patient by ID "
        def obtainedDTO = patientService.getPatient(1L)

        then: "comparing sample Patient and obtained one"
        patientDTO == obtainedDTO

        then: "no calls for Service"
        0 * patientService.getPatient(1L)
    }

    def "Donor Service creates Patient Entity in DB"() {
        given: "Patient with sample field values"
        def patientModelMapped = new PatientModel(id: 1L, name: "Ivan")
        def patientDTOMapped = new PatientDTO(id: 1L, name: "Ivan")

        when: "creating Patient in service"
        def actualDTO = patientService.createPatient(patientDTO)

        then: "Repository saves and returns entity"
        1 * patientMapper.toPatientModel(patientDTO, context) >> patientModel
        1 * patientRepository.save(patientModel) >> patientModelMapped
        1 * patientMapper.toPatientDTO(patientModelMapped, context) >> patientDTOMapped

        then: "Comparing obtained Donor and expected"
        actualDTO == patientDTOMapped

        then: "No calls for service"
        0 * patientService.createPatient(patientDTO)
    }

    def "Patient Service returns all Patients"() {
        given: "2nd DTO and Model"
        def patientDTO2 = new PatientDTO(id: 1L, name: "Petr")
        def patientModel2 = new PatientModel(id: 1L, name: "Petr")
        List<PatientDTO> expected = List.of(patientDTO, patientDTO2)
        Page<PatientModel> page = new PageImpl<>(List.of(patientModel, patientModel2))

        when: "Requesting All Donors"
        List<PatientDTO> actual = patientService.getAllPatients(PageRequest.of(0, 10))

        then: "DonorRepository returns list of donors"
        1 * patientRepository.findAll(PageRequest.of(0, 10)) >> page

        then: "Mapping Entities to DTO"
        1 * patientMapper.toPatientDTO(patientModel, context) >> patientDTO
        1 * patientMapper.toPatientDTO(patientModel2, context) >> patientDTO2

        then: "Comparing Lists"
        actual == expected
    }

}
