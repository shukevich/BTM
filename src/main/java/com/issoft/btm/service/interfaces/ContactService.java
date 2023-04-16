package com.issoft.btm.service.interfaces;

import com.issoft.btm.dto.ContactDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {
    ContactDTO createContact(ContactDTO dto);

    ContactDTO getContact(Long id);

    ContactDTO updateContact(ContactDTO contactDTO);

    void deleteContact(Long id);

    List<ContactDTO> getAllContacts(Pageable page);
}
