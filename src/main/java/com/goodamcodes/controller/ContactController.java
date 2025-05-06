package com.goodamcodes.controller;

import com.goodamcodes.dto.ContactDTO;
import com.goodamcodes.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactDTO>  addContact(@RequestBody ContactDTO contactDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.addContact(contactDTO));
    }

    @PostMapping("/all")
    public ResponseEntity<List<ContactDTO>>  addAllContacts(@RequestBody List<ContactDTO> contactDTOs) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.addAllContacts(contactDTOs));
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getContacts(){
        return ResponseEntity.status(HttpStatus.OK).body(contactService.getContacts());
    }

    @PatchMapping(path = "/{contactId}")
    public ResponseEntity<ContactDTO>  updateContact(@PathVariable("contactId") Long contactId, @RequestBody ContactDTO contactDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.updateContact(contactId,contactDTO));
    }

    @DeleteMapping(path = "/{contactId}")
    public ResponseEntity<String> deleteContact(@PathVariable("contactId") Long contactId){
        return ResponseEntity.status(HttpStatus.OK).body(contactService.deleteContact(contactId));
    }

}
