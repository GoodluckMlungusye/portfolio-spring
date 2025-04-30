package com.goodamcodes.controller;
import com.goodamcodes.dto.Skill.SkillRequestDTO;
import com.goodamcodes.dto.Skill.SkillResponseDTO;
import com.goodamcodes.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillResponseDTO> addSkill(@RequestBody SkillRequestDTO skillRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.addSkill(skillRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<SkillResponseDTO>> getSkills(){
        return ResponseEntity.status(HttpStatus.OK).body(skillService.getSkills());
    }

    @PatchMapping(path = "/{skillId}")
    public ResponseEntity<SkillResponseDTO> updateSkill(@PathVariable("skillId") Long skillId,  @RequestBody SkillRequestDTO skillRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.updateSkill(skillId, skillRequestDTO));
    }

    @DeleteMapping(path = "/{skillId}")
    public ResponseEntity<String> deleteSkill(@PathVariable("skillId") Long skillId){
        return ResponseEntity.status(HttpStatus.OK).body(skillService.deleteSkill(skillId));
    }

}
