package com.goodamcodes.controller;
import com.goodamcodes.dto.ExploreDTO;
import com.goodamcodes.service.ExploreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/explore")
public class ExploreController {

    @Autowired
    private ExploreService exploreService;

    @PostMapping
    public ResponseEntity<ExploreDTO> addExplore(@RequestPart("explore") ExploreDTO exploreDTO, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(exploreService.addExplore(exploreDTO, file));
    }

    @PostMapping("/all")
    public ResponseEntity<List<ExploreDTO>> addAllExplores(@RequestPart("explores") List<ExploreDTO> exploreDTOs, @RequestPart("files") List<MultipartFile> files){
        return ResponseEntity.status(HttpStatus.CREATED).body(exploreService.addAllExplores(exploreDTOs, files));
    }

    @GetMapping
    public ResponseEntity<List<ExploreDTO>> getExploreList(){
        return ResponseEntity.status(HttpStatus.OK).body(exploreService.getExploreList());
    }

    @PatchMapping(path = "/{exploreId}")
    public ResponseEntity<ExploreDTO> updateExplore(@PathVariable("exploreId") Long exploreId, @RequestPart("explore") ExploreDTO exploreDTO, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(exploreService.updateExplore(exploreId, exploreDTO, file));
    }

    @DeleteMapping(path = "/{exploreId}")
    public ResponseEntity<String> deleteExplore(@PathVariable("exploreId") Long exploreId){
        return ResponseEntity.status(HttpStatus.OK).body(exploreService.deleteExplore(exploreId));
    }

}
