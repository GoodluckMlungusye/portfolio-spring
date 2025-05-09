package com.goodamcodes.controller;

import com.goodamcodes.dto.ClientDTO;
import com.goodamcodes.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addClient(clientDTO));
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getClients(){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClients());
    }

    @DeleteMapping(path = "/{clientId}")
    public ResponseEntity<String>  deleteClient(@PathVariable("clientId") Long clientId){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.deleteClient(clientId));
    }

}
