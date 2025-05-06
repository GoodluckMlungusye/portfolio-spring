package com.goodamcodes.controller;
import com.goodamcodes.dto.SkillDTO;
import com.goodamcodes.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillDTO> addSkill(@RequestBody SkillDTO skillDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.addSkill(skillDTO));
    }

    @PostMapping("/all")
    public ResponseEntity<List<SkillDTO>> addAllSkills(@RequestBody List<SkillDTO> skillDTOS) {
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.addAllSkills(skillDTOS));
    }

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getSkills(){
        return ResponseEntity.status(HttpStatus.OK).body(skillService.getSkills());
    }

    @PatchMapping(path = "/{skillId}")
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable("skillId") Long skillId,  @RequestBody SkillDTO skillDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.updateSkill(skillId, skillDTO));
    }

    @DeleteMapping(path = "/{skillId}")
    public ResponseEntity<String> deleteSkill(@PathVariable("skillId") Long skillId){
        return ResponseEntity.status(HttpStatus.OK).body(skillService.deleteSkill(skillId));
    }

}
