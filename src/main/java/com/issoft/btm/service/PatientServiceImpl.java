package com.issoft.btm.service;

import com.issoft.btm.dto.PatientDTO;
import com.issoft.btm.mapper.PatientMapper;
import com.issoft.btm.mapper.util.CycleAvoidingMappingContext;
import com.issoft.btm.model.PatientModel;
import com.issoft.btm.repository.PatientRepository;
import com.issoft.btm.service.interfaces.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final CycleAvoidingMappingContext context;


    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        PatientModel patient = patientRepository.save(patientMapper.toPatientModel(patientDTO, context));
        return patientMapper.toPatientDTO(patient, context);
    }

    @Override
    public PatientDTO getPatient(Long id) {
        return patientMapper.toPatientDTO(patientRepository.findById(id).orElseThrow(IllegalArgumentException::new), context);
    }

    @Override
    public PatientDTO updatePatient(PatientDTO patientDTO, Long patientId) {
        patientDTO.setId(patientId);
        PatientModel patient = patientMapper.toPatientModel(patientDTO, context);
        return patientMapper.toPatientDTO(patientRepository.save(patient), context);
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public List<PatientDTO> getAllPatients(Pageable page) {
        return patientRepository.findAll(page)
                .stream()
                .map(patientModel -> patientMapper.toPatientDTO(patientModel, context))
                .collect(Collectors.toList());
    }
}
