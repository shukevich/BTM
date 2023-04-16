package com.issoft.btm.service;

import com.issoft.btm.dto.DonorDTO;
import com.issoft.btm.mapper.DonorMapper;
import com.issoft.btm.mapper.util.CycleAvoidingMappingContext;
import com.issoft.btm.model.DonorModel;
import com.issoft.btm.repository.DonorRepository;
import com.issoft.btm.service.interfaces.DonorService;
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
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;
    private final DonorMapper donorMapper;
    private final CycleAvoidingMappingContext context;


    @Override
    public DonorDTO createDonor(DonorDTO donorDTO) {
        DonorModel model = donorRepository.save(donorMapper.toDonorModel(donorDTO, context));
        return donorMapper.toDonorDTO(model, context);
    }

    @Override
    public DonorDTO getDonor(Long id) {
        return donorMapper
                .toDonorDTO(donorRepository.findById(id).orElseThrow(IllegalArgumentException::new), context);
    }

    @Override
    public DonorDTO updateDonor(DonorDTO donorDTO, Long donorId) {
        donorDTO.setId(donorId);
        DonorModel donorModel = donorMapper.toDonorModel(donorDTO, context);
        return donorMapper.toDonorDTO(donorRepository.save(donorModel), context);

    }

    @Override
    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }

    @Override
    public List<DonorDTO> getAllDonors(Pageable page) {
        return donorRepository.findAll(page)
                .stream()
                .map(donorModel -> donorMapper.toDonorDTO(donorModel, context))
                .collect(Collectors.toList());
    }
}
