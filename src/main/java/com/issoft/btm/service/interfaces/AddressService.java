package com.issoft.btm.service.interfaces;

import com.issoft.btm.dto.AddressDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO dto);

    AddressDTO getAddress(Long id);

    AddressDTO updateAddress(AddressDTO addressDTO);

    void deleteAddress(Long id);

    List<AddressDTO> getAllAddresses(Pageable page);

}
