package com.goodamcodes.service;
import com.goodamcodes.dto.SubSkillDTO;
import com.goodamcodes.mapper.SubSkillMapper;
import com.goodamcodes.model.Skill;
import com.goodamcodes.model.SubSkill;
import com.goodamcodes.repository.SkillRepository;
import com.goodamcodes.repository.SubSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubSkillService {

    @Autowired
    private SubSkillRepository subSkillRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SubSkillMapper subSkillMapper;

    public SubSkillDTO addSubSkill(SubSkillDTO subSkillDTO){
        Optional<SubSkill> existingSubSkill = subSkillRepository.findByName(subSkillDTO.getName());
        if(existingSubSkill.isPresent()){
            throw new IllegalStateException("SubSkill exists");
        }

        Skill skill = skillRepository.findById(subSkillDTO.getSkillId())
                .orElseThrow(() -> new IllegalStateException("Skill not found"));

        SubSkill subSkill = subSkillMapper.toSubSkill(subSkillDTO);
        subSkill.setSkill(skill);
        SubSkill savedSubSkill = subSkillRepository.save(subSkill);
        return subSkillMapper.toSubSkillDTO(savedSubSkill);
    }

    public List<SubSkillDTO> getSubSkills(){
        List<SubSkill> subSkills = subSkillRepository.findAll();
        return subSkillMapper.toSubSkillDTOs(subSkills);
    }

    public SubSkillDTO updateSubSkill(Long subSkillId, SubSkillDTO subSkillDTO){
        SubSkill existingSubSkill = subSkillRepository.findById(subSkillId).orElseThrow(
                () -> new IllegalStateException("SubSkill does not exist")
        );
        subSkillMapper.updateSubSkillFromDTO(subSkillDTO, existingSubSkill);

        if(subSkillDTO.getSkillId() != null && !subSkillDTO.getSkillId().equals(existingSubSkill.getSkill().getId())) {
            Skill newSkill = skillRepository.findById(subSkillDTO.getSkillId()).orElseThrow(
                    () -> new IllegalStateException("Skill does not exist")
            );
            existingSubSkill.setSkill(newSkill);
        }

        SubSkill updatedSubSkill = subSkillRepository.save(existingSubSkill);
        return subSkillMapper.toSubSkillDTO(updatedSubSkill);
    }

    public String deleteSubSkill(Long subSkillId){
        boolean isExisting = subSkillRepository.existsById(subSkillId);
        if(!isExisting){
            throw new IllegalStateException("SubSkill does not exist");
        }
        subSkillRepository.deleteById(subSkillId);
        return "Product with id: " + subSkillId + " has been deleted";
    }

}
