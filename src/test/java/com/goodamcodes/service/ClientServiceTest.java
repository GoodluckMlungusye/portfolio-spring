package com.goodamcodes.service;

import com.goodamcodes.dto.ClientDTO;
import com.goodamcodes.mapper.ClientMapper;
import com.goodamcodes.model.Client;
import com.goodamcodes.repository.ClientRepository;
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
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    private ClientDTO clientDTO;
    private Client client;

    @BeforeEach
    void setup() {
        clientDTO = new ClientDTO();
        clientDTO.setEmail("test@example.com");

        client = new Client();
        client.setEmail("test@example.com");
    }

    @Nested
    @DisplayName("addClient() Tests")
    class AddClientTests {

        @Test
        void shouldAddNewClient() {
            when(clientRepository.findByEmail(clientDTO.getEmail())).thenReturn(Optional.empty());
            when(clientMapper.toClient(clientDTO)).thenReturn(client);
            when(clientRepository.save(client)).thenReturn(client);
            when(clientMapper.toClientDTO(client)).thenReturn(clientDTO);

            ClientDTO result = clientService.addClient(clientDTO);

            assertNotNull(result);
            verify(clientRepository).save(client);
        }

        @Test
        void shouldThrowIfClientEmailExists() {
            when(clientRepository.findByEmail(clientDTO.getEmail())).thenReturn(Optional.of(client));

            assertThrows(IllegalStateException.class, () -> clientService.addClient(clientDTO));
            verify(clientRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("getClients() Tests")
    class GetClientsTests {

        @Test
        void shouldReturnClientDTOList() {
            List<Client> clients = List.of(client);
            List<ClientDTO> clientDTOs = List.of(clientDTO);

            when(clientRepository.findAll()).thenReturn(clients);
            when(clientMapper.toClientDTOs(clients)).thenReturn(clientDTOs);

            List<ClientDTO> result = clientService.getClients();

            assertEquals(1, result.size());
            verify(clientRepository).findAll();
            verify(clientMapper).toClientDTOs(clients);
        }
    }

    @Nested
    @DisplayName("deleteClient() Tests")
    class DeleteClientTests {

        @Test
        void shouldDeleteExistingClient() {
            when(clientRepository.existsById(1L)).thenReturn(true);

            String result = clientService.deleteClient(1L);

            assertTrue(result.contains("1"));
            verify(clientRepository).deleteById(1L);
        }

        @Test
        void shouldThrowIfClientDoesNotExist() {
            when(clientRepository.existsById(1L)).thenReturn(false);

            assertThrows(IllegalStateException.class, () -> clientService.deleteClient(1L));
            verify(clientRepository, never()).deleteById(anyLong());
        }
    }
}

