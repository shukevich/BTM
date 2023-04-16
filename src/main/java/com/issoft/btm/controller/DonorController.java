package com.issoft.btm.controller;

import com.issoft.btm.dto.DonorDTO;
import com.issoft.btm.service.interfaces.DonorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequestMapping("/donor")
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;

    @PostMapping("/createDonor")
    public ResponseEntity<DonorDTO> createDonor(@RequestBody @Valid DonorDTO donorDTO) {
        return ResponseEntity.ok(donorService.createDonor(donorDTO));
    }

    @PutMapping("/updateDonor/{donorId}")
    public ResponseEntity<DonorDTO> updateDonor(@RequestBody @Valid DonorDTO donorDTO, @PathVariable Long donorId) {
        return ResponseEntity.ok(donorService.updateDonor(donorDTO, donorId));
    }

    @GetMapping("/getDonor/{donorId}")
    public ResponseEntity<DonorDTO> getDonor(@PathVariable @NotNull Long donorId) {
        return ResponseEntity.ok(donorService.getDonor(donorId));
    }

    @DeleteMapping("/deleteDonor/{donorId}")
    public void deleteDonor(@PathVariable @NotNull Long donorId) {
        donorService.deleteDonor(donorId);
    }

}
