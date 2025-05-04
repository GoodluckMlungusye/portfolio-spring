package com.goodamcodes.service;

import com.goodamcodes.dto.SkillDTO;
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

    public SkillDTO addSkill(SkillDTO skillDTO) {
        Optional<Skill> existingSkill = skillRepository.findByName(skillDTO.getName());
        if(existingSkill.isPresent()){
            throw new IllegalStateException("Skill exists");
        }
        Skill skill = skillMapper.toSkill(skillDTO);
        Skill savedSkill = skillRepository.save(skill);
        return skillMapper.toSkillDTO(savedSkill);
    }

    public List<SkillDTO> addAllSkills(List<SkillDTO> skillDTOs) {
        List<Skill> skills = skillMapper.toSkills(skillDTOs);

        List<Skill> newSkills = skills.stream()
                .filter(skill -> skillRepository.findByName(skill.getName()).isEmpty())
                .toList();

        List<Skill> savedSkills = skillRepository.saveAll(newSkills);
        return skillMapper.toSkillDTOs(savedSkills);
    }


    public List<SkillDTO> getSkills(){
        List<Skill> skills = skillRepository.findAllWithSubSkills();
        return skillMapper.toSkillDTOs(skills);
    }

    public SkillDTO updateSkill(Long skillId, SkillDTO skillDTO){
        Skill existingSkill = skillRepository.findById(skillId).orElseThrow(
                () -> new IllegalStateException("Skill does not exist")
        );
        skillMapper.updateSkillFromDTO(skillDTO, existingSkill);
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
