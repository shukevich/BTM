package com.issoft.btm.service;

import com.issoft.btm.dto.AddressDTO;
import com.issoft.btm.mapper.AddressMapper;
import com.issoft.btm.model.AddressModel;
import com.issoft.btm.repository.AddressRepository;
import com.issoft.btm.service.interfaces.AddressService;
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
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;


    @Override
    public AddressDTO createAddress(AddressDTO dto) {
        AddressModel model = addressRepository.save(addressMapper.toAddressModel(dto));
        return addressMapper.toAddressDTO(model);

    }

    @Override
    public AddressDTO getAddress(Long id) {
        return addressMapper
                .toAddressDTO(addressRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) {
        AddressModel addressModel = addressMapper.toAddressModel(addressDTO);
        return addressMapper.toAddressDTO(addressRepository.save(addressModel));

    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public List<AddressDTO> getAllAddresses(Pageable page) {
        return addressRepository.findAll(page)
                .stream()
                .map(addressMapper::toAddressDTO)
                .collect(Collectors.toList());
    }
}
