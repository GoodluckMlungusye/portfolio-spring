package com.goodamcodes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodamcodes.dto.ClientDTO;
import com.goodamcodes.service.ClientService;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddClient() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("John Doe");

        when(clientService.addClient(any(ClientDTO.class))).thenReturn(clientDTO);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void shouldGetClients() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Alice");

        when(clientService.getClients()).thenReturn(List.of(clientDTO));

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    void shouldDeleteClient() throws Exception {
        when(clientService.deleteClient(1L)).thenReturn("Client with id: 1 has been deleted");

        mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Client with id: 1 has been deleted"));
    }
}
