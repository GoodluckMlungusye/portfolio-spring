package com.goodamcodes.controller;
import com.goodamcodes.dto.SubSkillDTO;
import com.goodamcodes.service.SubSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subskills")
public class SubSkillController {
    @Autowired
    private SubSkillService subSkillService;

    @PostMapping
    public ResponseEntity<SubSkillDTO> addSubSkill(@RequestBody SubSkillDTO subSkillDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(subSkillService.addSubSkill(subSkillDTO));
    }

    @PostMapping("/all")
    public ResponseEntity<List<SubSkillDTO>> addAllSubSkills(@RequestBody List<SubSkillDTO> subSkillDTOS){
        return ResponseEntity.status(HttpStatus.CREATED).body(subSkillService.addAllSubSkills(subSkillDTOS));
    }

    @GetMapping
    public ResponseEntity<List<SubSkillDTO>> getSubSkills(){
        return ResponseEntity.status(HttpStatus.OK).body(subSkillService.getSubSkills());
    }

    @PatchMapping(path = "/{subSkillId}")
    public ResponseEntity<SubSkillDTO> updateSubSkill(@PathVariable("subSkillId") Long subSkillId, @RequestBody SubSkillDTO subSkillDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(subSkillService.updateSubSkill(subSkillId, subSkillDTO));
    }

    @DeleteMapping(path = "/{subSkillId}")
    public ResponseEntity<String> deleteSubSkill(@PathVariable("subSkillId") Long subSkillId){
        return ResponseEntity.status(HttpStatus.OK).body(subSkillService.deleteSubSkill(subSkillId));
    }

}
