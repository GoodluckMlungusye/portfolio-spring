package com.goodamcodes.service;
import com.goodamcodes.dto.SubSkillDTO;
import com.goodamcodes.mapper.SkillMapper;
import com.goodamcodes.mapper.SubSkillMapper;
import com.goodamcodes.model.Skill;
import com.goodamcodes.model.SubSkill;
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
    private SubSkillMapper subSkillMapper;

    @Autowired
    private SkillMapper skillMapper;

    public SubSkillDTO addSubSkill(SubSkillDTO subSkillDTO){
        Optional<SubSkill> existingSubSkill = subSkillRepository.findByName(subSkillDTO.getName());
        if(existingSubSkill.isPresent()){
            throw new IllegalStateException("SubSkill exists");
        }
        SubSkill subSkill = subSkillMapper.toSubSkill(subSkillDTO);
        if(subSkillDTO.getSkill().getName() != null && !subSkillDTO.getSkill().getName().isEmpty()){
            Skill addedSkill = skillMapper.toSkill(subSkillDTO.getSkill());
            subSkill.setSkill(addedSkill);
        }
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

        if(subSkillDTO.getSkill().getName() != null && !subSkillDTO.getSkill().getName().isEmpty()){
            if(existingSubSkill.getSkill().getName() == null){
                Skill updatedSkill = skillMapper.toSkill(subSkillDTO.getSkill());
                existingSubSkill.setSkill(updatedSkill);
            }else{
                subSkillMapper.updateSubSkillFromDTO(subSkillDTO, existingSubSkill);
            }
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
