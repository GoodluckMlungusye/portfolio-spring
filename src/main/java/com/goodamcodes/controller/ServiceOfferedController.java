package com.goodamcodes.controller;
import com.goodamcodes.dto.ServiceOfferedDTO;
import com.goodamcodes.service.ServiceOfferedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceOfferedController {

    private final ServiceOfferedService serviceOfferedService;

    @PostMapping
    public ResponseEntity<ServiceOfferedDTO> addServiceOffered(@RequestPart("service") ServiceOfferedDTO serviceOfferedDTO,  @RequestParam("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceOfferedService.addServiceOffered(serviceOfferedDTO, file));
    }

    @PostMapping("/all")
    public ResponseEntity<List<ServiceOfferedDTO>> addAllServicesOffered(@RequestPart("services") List<ServiceOfferedDTO> serviceOfferedDTOS,  @RequestParam("files") List<MultipartFile> files){
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceOfferedService.addAllServicesOffered(serviceOfferedDTOS, files));
    }

    @GetMapping
    public ResponseEntity<List<ServiceOfferedDTO>> getServiceOfferedList(){
        return ResponseEntity.status(HttpStatus.OK).body(serviceOfferedService.getServiceOfferedList());
    }

    @PatchMapping(path = "/{serviceOfferedId}")
    public ResponseEntity<ServiceOfferedDTO> updateServiceOffered(@PathVariable("serviceOfferedId") Long serviceOfferedId, @RequestPart("service") ServiceOfferedDTO serviceOfferedDTO, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(serviceOfferedService.updateServiceOffered(serviceOfferedId, serviceOfferedDTO, file));
    }

    @DeleteMapping(path = "/{serviceOfferedId}")
    public ResponseEntity<String> deleteServiceOffered(@PathVariable("serviceOfferedId") Long serviceOfferedId){
        return ResponseEntity.status(HttpStatus.OK).body(serviceOfferedService.deleteServiceOffered(serviceOfferedId));
    }

}
