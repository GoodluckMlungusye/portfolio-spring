package com.goodamcodes.service;

import com.goodamcodes.dto.ContactDTO;
import com.goodamcodes.mapper.ContactMapper;
import com.goodamcodes.model.Contact;
import com.goodamcodes.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private ContactService contactService;

    private ContactDTO contactDTO;
    private Contact contact;

    @BeforeEach
    void setup() {
        contactDTO = new ContactDTO();
        contactDTO.setMedium("Email");

        contact = new Contact();
        contact.setMedium("Email");
    }

    @Nested
    @DisplayName("addContact() Tests")
    class AddContactTests {

        @Test
        void shouldAddNewContact() {
            when(contactRepository.findByMedium("Email")).thenReturn(Optional.empty());
            when(contactMapper.toContact(contactDTO)).thenReturn(contact);
            when(contactRepository.save(contact)).thenReturn(contact);
            when(contactMapper.toContactDTO(contact)).thenReturn(contactDTO);

            ContactDTO result = contactService.addContact(contactDTO);

            assertNotNull(result);
            verify(contactRepository).save(contact);
        }

        @Test
        void shouldThrowIfContactMediumExists() {
            when(contactRepository.findByMedium("Email")).thenReturn(Optional.of(contact));

            assertThrows(IllegalStateException.class, () -> contactService.addContact(contactDTO));
            verify(contactRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("addAllContacts() Tests")
    class AddAllContactsTests {

        @Test
        void shouldAddOnlyNewContacts() {
            List<ContactDTO> dtoList = List.of(contactDTO);
            List<Contact> contactList = List.of(contact);

            when(contactMapper.toContacts(dtoList)).thenReturn(contactList);
            when(contactRepository.findByMedium("Email")).thenReturn(Optional.empty());
            when(contactRepository.saveAll(contactList)).thenReturn(contactList);
            when(contactMapper.toContactDTOs(contactList)).thenReturn(dtoList);

            List<ContactDTO> result = contactService.addAllContacts(dtoList);

            assertEquals(1, result.size());
            verify(contactRepository).saveAll(contactList);
        }
    }

    @Nested
    @DisplayName("getContacts() Tests")
    class GetContactsTests {

        @Test
        void shouldReturnAllMappedContacts() {
            List<Contact> contactList = List.of(contact);
            List<ContactDTO> dtoList = List.of(contactDTO);

            when(contactRepository.findAll()).thenReturn(contactList);
            when(contactMapper.toContactDTOs(contactList)).thenReturn(dtoList);

            List<ContactDTO> result = contactService.getContacts();

            assertEquals(1, result.size());
            verify(contactRepository).findAll();
        }
    }

    @Nested
    @DisplayName("updateContact() Tests")
    class UpdateContactTests {

        @Test
        void shouldUpdateExistingContact() {
            when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
            doNothing().when(contactMapper).updateContactFromDTO(contactDTO, contact);
            when(contactRepository.save(contact)).thenReturn(contact);
            when(contactMapper.toContactDTO(contact)).thenReturn(contactDTO);

            ContactDTO result = contactService.updateContact(1L, contactDTO);

            assertNotNull(result);
            verify(contactRepository).save(contact);
        }

        @Test
        void shouldThrowIfContactNotFound() {
            when(contactRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(IllegalStateException.class, () -> contactService.updateContact(999L, contactDTO));
        }
    }

    @Nested
    @DisplayName("deleteContact() Tests")
    class DeleteContactTests {

        @Test
        void shouldDeleteContactIfExists() {
            when(contactRepository.existsById(1L)).thenReturn(true);

            String result = contactService.deleteContact(1L);

            assertTrue(result.contains("1"));
            verify(contactRepository).deleteById(1L);
        }

        @Test
        void shouldThrowIfContactNotExists() {
            when(contactRepository.existsById(99L)).thenReturn(false);

            assertThrows(IllegalStateException.class, () -> contactService.deleteContact(99L));
            verify(contactRepository, never()).deleteById(anyLong());
        }
    }
}
