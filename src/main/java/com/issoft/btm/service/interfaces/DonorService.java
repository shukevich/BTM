package com.issoft.btm.service.interfaces;

import com.issoft.btm.dto.DonorDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DonorService {

    DonorDTO createDonor(DonorDTO donorDTO);

    DonorDTO getDonor(Long id);

    DonorDTO updateDonor(DonorDTO donorDTO, Long donorId);

    void deleteDonor(Long id);

    List<DonorDTO> getAllDonors(Pageable page);

}
