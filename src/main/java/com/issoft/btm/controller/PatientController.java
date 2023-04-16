package com.issoft.btm.controller;

import com.issoft.btm.dto.PatientDTO;
import com.issoft.btm.service.interfaces.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;


    @PostMapping("/createPatient")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) {
        return ResponseEntity.ok(patientService.createPatient(patientDTO));
    }

    @PutMapping("/updatePatient/{patientId}")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody @Valid PatientDTO patientDTO, @PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.updatePatient(patientDTO, patientId));
    }

    @GetMapping("/getPatient/{patientId}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable @NotNull Long patientId) {
        return ResponseEntity.ok(patientService.getPatient(patientId));
    }

    @DeleteMapping("/deletePatient")
    public void deletePatient(@RequestBody @NotNull Long patientId) {
        patientService.deletePatient(patientId);
    }
}
