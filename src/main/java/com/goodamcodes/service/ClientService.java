package com.goodamcodes.service;

import com.goodamcodes.dto.ClientDTO;
import com.goodamcodes.mapper.ClientMapper;
import com.goodamcodes.model.Client;
import com.goodamcodes.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientDTO addClient(ClientDTO clientDTO){
        Optional<Client> existingClient = clientRepository.findByEmail(clientDTO.getEmail());
        if(existingClient.isPresent()){
            throw new IllegalStateException("Client with email " + clientDTO.getEmail()+ " already exists");
        }

        Client client = clientMapper.toClient(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toClientDTO(savedClient);
    }


    public List<ClientDTO> getClients(){
        List<Client> clients = clientRepository.findAll();
        return clientMapper.toClientDTOs(clients);
    }


    public String deleteClient(Long clientId){
        boolean isExisting = clientRepository.existsById(clientId);
        if(!isExisting){
            throw new IllegalStateException("Client does not exist");
        }
        clientRepository.deleteById(clientId);
        return "Client with id: " + clientId + " has been deleted";
    }

}
