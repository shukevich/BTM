package com.issoft.btm.service;

import com.issoft.btm.dto.ContactDTO;
import com.issoft.btm.mapper.ContactMapper;
import com.issoft.btm.model.ContactModel;
import com.issoft.btm.repository.ContactRepository;
import com.issoft.btm.service.interfaces.ContactService;
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
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;


    @Override
    public ContactDTO createContact(ContactDTO dto) {
            ContactModel model = contactRepository.save(contactMapper.toContactModel(dto));
            return contactMapper.toContactDTO(model);

    }

    @Override
    public ContactDTO getContact(Long id) {
        return contactMapper
                .toContactDTO(contactRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public ContactDTO updateContact(ContactDTO contactDTO) {
            ContactModel contactModel = contactMapper.toContactModel(contactDTO);
            return contactMapper.toContactDTO(contactRepository.save(contactModel));
    }

    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactDTO> getAllContacts(Pageable page) {
        return contactRepository.findAll(page)
                .stream()
                .map(contactMapper::toContactDTO)
                .collect(Collectors.toList());
    }
}
