package com.goodamcodes.service;

import com.goodamcodes.dto.ContactDTO;
import com.goodamcodes.mapper.ContactMapper;
import com.goodamcodes.model.Contact;
import com.goodamcodes.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    public ContactDTO addContact(ContactDTO contactDTO){
        Optional<Contact> existingContact = contactRepository.findByMedium(contactDTO.getMedium());
        if(existingContact.isPresent()){
            throw new IllegalStateException(contactDTO.getMedium()+ " contact exists");
        }
        Contact contact = contactMapper.toContact(contactDTO);
        Contact savedContact = contactRepository.save(contact);
        return contactMapper.toContactDTO(savedContact);
    }

    public List<ContactDTO> getContacts(){
        List<Contact> contacts = contactRepository.findAll();
        return contactMapper.toContactDTOs(contacts);
    }

    public ContactDTO updateContact(Long contactId, ContactDTO contactDTO){
        Contact existingContact = contactRepository.findById(contactId).orElseThrow(
                () -> new IllegalStateException("Contact does not exist")
        );

        contactMapper.updateContactFromDTO(contactDTO, existingContact);
        Contact updatedContact = contactRepository.save(existingContact);
        return contactMapper.toContactDTO(updatedContact);
    }

    public String deleteContact(Long contactId){
        boolean isExisting = contactRepository.existsById(contactId);
        if(!isExisting){
            throw new IllegalStateException("Contact does not exist");
        }
        contactRepository.deleteById(contactId);
        return "Contact with id: " + contactId + " has been deleted";
    }

}
