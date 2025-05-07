package com.goodamcodes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodamcodes.dto.ContactDTO;
import com.goodamcodes.service.ContactService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddContact() throws Exception {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setMedium("Email");
        contactDTO.setContactLink("contact@example.com");

        when(contactService.addContact(any(ContactDTO.class))).thenReturn(contactDTO);

        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.medium").value("Email"))
                .andExpect(jsonPath("$.contactLink").value("contact@example.com"));
    }

    @Test
    void shouldAddAllContacts() throws Exception {
        ContactDTO contactDTO1 = new ContactDTO();
        contactDTO1.setMedium("Phone");
        contactDTO1.setContactLink("123-456-7890");

        ContactDTO contactDTO2 = new ContactDTO();
        contactDTO2.setMedium("LinkedIn");
        contactDTO2.setContactLink("linkedin.com/in/johndoe");

        when(contactService.addAllContacts(any(List.class))).thenReturn(List.of(contactDTO1, contactDTO2));

        mockMvc.perform(post("/contacts/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(contactDTO1, contactDTO2))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].medium").value("Phone"))
                .andExpect(jsonPath("$[1].contactLink").value("linkedin.com/in/johndoe"));
    }

    @Test
    void shouldGetContacts() throws Exception {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setMedium("Email");
        contactDTO.setContactLink("contact@example.com");

        when(contactService.getContacts()).thenReturn(List.of(contactDTO));

        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].medium").value("Email"))
                .andExpect(jsonPath("$[0].contactLink").value("contact@example.com"));
    }

    @Test
    void shouldUpdateContact() throws Exception {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setMedium("Updated Medium");
        contactDTO.setContactLink("updated@example.com");

        when(contactService.updateContact(Mockito.anyLong(), any(ContactDTO.class))).thenReturn(contactDTO);

        mockMvc.perform(patch("/contacts/{contactId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.medium").value("Updated Medium"))
                .andExpect(jsonPath("$.contactLink").value("updated@example.com"));
    }

    @Test
    void shouldDeleteContact() throws Exception {
        when(contactService.deleteContact(1L)).thenReturn("Contact with id: 1 has been deleted");
        mockMvc.perform(delete("/contacts/{contactId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Contact with id: 1 has been deleted"));
    }
}

