package com.goodamcodes.service;

import com.goodamcodes.dto.Skill.SkillRequestDTO;
import com.goodamcodes.dto.Skill.SkillResponseDTO;
import com.goodamcodes.mapper.SkillMapper;
import com.goodamcodes.model.Skill;
import com.goodamcodes.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    SkillMapper skillMapper;

    public SkillResponseDTO addSkill(SkillRequestDTO skillRequestDTO) {
        Optional<Skill> existingSkill = skillRepository.findByName(skillRequestDTO.getName());
        if(existingSkill.isPresent()){
            throw new IllegalStateException("Skill exists");
        }
        Skill skill = skillMapper.toSkill(skillRequestDTO);
        Skill savedSkill = skillRepository.save(skill);
        return skillMapper.toSkillDTO(savedSkill);
    }

    public List<SkillResponseDTO> getSkills(){
        List<Skill> skills = skillRepository.findAll();
        return skillMapper.toSkillDTOs(skills);
    }

    public SkillResponseDTO updateSkill(Long skillId, SkillRequestDTO skillRequestDTO){
        Skill existingSkill = skillRepository.findById(skillId).orElseThrow(
                () -> new IllegalStateException("Skill does not exist")
        );
        skillMapper.updateSkillFromDTO(skillRequestDTO, existingSkill);
        Skill updatedSkill = skillRepository.save(existingSkill);
        return skillMapper.toSkillDTO(updatedSkill);
    }

    public String deleteSkill(Long skillId){
        boolean isExisting = skillRepository.existsById(skillId);
        if(!isExisting){
            throw new IllegalStateException("Skill does not exist");
        }
        skillRepository.deleteById(skillId);
        return "Skill with id: " + skillId + " has been deleted";
    }

}
