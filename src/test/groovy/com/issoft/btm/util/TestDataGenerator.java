package com.issoft.btm.util;

import com.issoft.btm.dto.AddressDTO;
import com.issoft.btm.dto.ContactDTO;
import com.issoft.btm.dto.DonorDTO;
import com.issoft.btm.dto.PatientDTO;
import com.issoft.btm.model.DonorModel;
import com.issoft.btm.model.PatientModel;
import com.issoft.btm.model.dictionaries.enums.AddressTypeEnum;
import com.issoft.btm.model.dictionaries.enums.ContactTypeEnum;

public class TestDataGenerator {

    public static DonorDTO generateDonorDTOv1() {
        DonorDTO donor1 = new DonorDTO();
        donor1.setName("Ivan");
        donor1.setSurname("Ivanov");
        donor1.setDonorType("Type1");
        donor1.setPhone("123456");
        return donor1;
    }


    public static DonorModel generateDonorModel() {
        DonorModel donorModel = new DonorModel();
        donorModel.setName("Ivan");
        donorModel.setDonorType("Type1");
        return donorModel;
    }

    public static PatientModel generatePatientModel() {
        PatientModel patientModel = new PatientModel();
        patientModel.setDiagnosis("Some Disease");
        patientModel.setName("Petr");
        return patientModel;
    }

    public static DonorDTO generateDonorDTOv2() {
        DonorDTO donor2 = new DonorDTO();
        donor2.setName("Masha");
        donor2.setSurname("Petrova");
        donor2.setDonorType("Type2");
        donor2.setPhone("12345678");
        return donor2;
    }

    public static PatientDTO generatePatientDTOv1() {
        PatientDTO patient1 = new PatientDTO();
        patient1.setName("Petr");
        patient1.setSurname("Petrov");
        patient1.setDiagnosis("disease1");
        patient1.setPhone("12346");
        return patient1;
    }


    public static PatientDTO generatePatientDTOv2() {
        PatientDTO patient2 = new PatientDTO();
        patient2.setName("Petr");
        patient2.setSurname("Sergeev");
        patient2.setDiagnosis("disease2");
        patient2.setPhone("1234678");
        return patient2;
    }


    public static ContactDTO generateContactDTOPickup() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactType(ContactTypeEnum.PICKUP);
        contactDTO.setName("Ivan");
        contactDTO.setSurname("Ivanov");
        return contactDTO;
    }

    public static ContactDTO generateContactDTODeliver() {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactType(ContactTypeEnum.DELIVER);
        contactDTO.setName("Ivan");
        contactDTO.setSurname("Ivanov");
        return contactDTO;
    }

    public static AddressDTO generateDeliverAddress() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressType(AddressTypeEnum.DELIVER);
        addressDTO.setCountry("USA");
        addressDTO.setCity("NY");
        addressDTO.setAddress("Address address");
        return addressDTO;
    }

    public static AddressDTO generatePickupAddress() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressType(AddressTypeEnum.PICKUP);
        addressDTO.setCountry("Canada");
        addressDTO.setCity("Vancouver");
        addressDTO.setAddress("Address address 22");
        return addressDTO;
    }
}
