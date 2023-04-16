package com.issoft.btm.service.interfaces;

import com.issoft.btm.dto.PatientDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {

    PatientDTO createPatient(PatientDTO patientDTO);

    PatientDTO getPatient(Long id);

    PatientDTO updatePatient(PatientDTO patientDTO, Long patientId);

    void deletePatient(Long id);

    List<PatientDTO> getAllPatients(Pageable page);

}
