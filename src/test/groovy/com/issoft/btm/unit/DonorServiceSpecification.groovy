package com.issoft.btm.unit

import com.issoft.btm.dto.DonorDTO
import com.issoft.btm.mapper.DonorMapper
import com.issoft.btm.mapper.util.CycleAvoidingMappingContext
import com.issoft.btm.model.DonorModel
import com.issoft.btm.repository.DonorRepository
import com.issoft.btm.service.DonorServiceImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class DonorServiceSpecification extends Specification {

    def donorRepository = Mock(DonorRepository)
    def donorMapper = Mock(DonorMapper)
    def context = Mock(CycleAvoidingMappingContext)
    def donorService = new DonorServiceImpl(donorRepository, donorMapper, context)
    def donorDTO = new DonorDTO(name: "Ivan")
    def donorModel = new DonorModel(name: "Ivan")

    def "Donor Service returns DonorDTO by requested DonorID"() {

        when: "requesting Donor by ID"
        def obtainedDTO = donorService.getDonor(1L)

        then: "Donor repository that always returns this donor"
        1 * donorRepository.findById(1L) >> Optional.of(donorModel)
        1 * donorMapper.toDonorDTO(donorModel, context) >> donorDTO

        then: "comparing sample Donor and obtained one"
        donorDTO == obtainedDTO

        then: "no calls for Service"
        0 * donorService.getDonor(1L)
    }

    def "Donor Service creates Donor Entity in DB"() {

        given: "Donor with sample field values"
        def donorModelMapped = new DonorModel(id: 1L, name: "Ivan")
        def donorDTOMapped = new DonorDTO(id: 1L, name: "Ivan")

        when: "creating Donor in service"
        def actualDTO = donorService.createDonor(donorDTO)

        then: "DonorDTO Converting to entity"
        1 * donorMapper.toDonorModel(donorDTO, context) >> donorModel
        1 * donorRepository.save(donorModel) >> donorModelMapped
        1 * donorMapper.toDonorDTO(donorModelMapped, context) >> donorDTOMapped

        then: "Comparing obtained Donor and expected"
        actualDTO == donorDTOMapped

        then: "No calls for service"
        0 * donorService.createDonor(donorDTO)
    }

    def "Donor Service returns all Donors"() {
        given: "2nd DTO and Model"
        def donorDTO2 = new DonorDTO(id: 2, name: "Petr")
        def donorModel2 = new DonorModel(id: 2, name: "Petr")
        List<DonorDTO> expected = List.of(donorDTO, donorDTO2)
        Page<DonorModel> page = new PageImpl<>(List.of(donorModel, donorModel2))

        when: "Requesting All Donors"
        List<DonorDTO> actual = donorService.getAllDonors(PageRequest.of(0, 10))

        then: "DonorRepository returns list of donors"
        1 * donorRepository.findAll(PageRequest.of(0, 10)) >> page
        1 * donorMapper.toDonorDTO(donorModel, context) >> donorDTO
        1 * donorMapper.toDonorDTO(donorModel2, context) >> donorDTO2

        then: "Comparing Lists"
        actual == expected
    }
}
