package com.goodamcodes.controller;
import com.goodamcodes.dto.NavigationLinkDTO;
import com.goodamcodes.service.NavigationLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/links")
public class NavigationLinkController {

    @Autowired
    private NavigationLinkService navigationLinkService;

    @PostMapping
    public ResponseEntity<NavigationLinkDTO> addNavigationLink(@RequestBody NavigationLinkDTO navigationLinkDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(navigationLinkService.addNavigationLink(navigationLinkDTO));
    }

    @PostMapping("/all")
    public ResponseEntity<List<NavigationLinkDTO>> addAllNavigationLinks(@RequestBody List<NavigationLinkDTO> navigationLinkDTOs){
        return ResponseEntity.status(HttpStatus.CREATED).body(navigationLinkService.addAllNavigationLinks(navigationLinkDTOs));
    }

    @GetMapping
    public ResponseEntity<List<NavigationLinkDTO>> getNavigationLinks(){
        return ResponseEntity.status(HttpStatus.OK).body(navigationLinkService.getNavigationLinks());
    }

    @PatchMapping(path = "/{navigationLinkId}")
    public ResponseEntity<NavigationLinkDTO> updateNavigationLink(@PathVariable("navigationLinkId") Long navigationLinkId, @RequestBody NavigationLinkDTO navigationLinkDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(navigationLinkService.updateNavigationLink(navigationLinkId, navigationLinkDTO));
    }

    @DeleteMapping(path = "/{navigationLinkId}")
    public ResponseEntity<String> deleteNavigationLink(@PathVariable("navigationLinkId") Long navigationLinkId){
        return ResponseEntity.status(HttpStatus.OK).body(navigationLinkService.deleteNavigationLink(navigationLinkId));
    }

}
