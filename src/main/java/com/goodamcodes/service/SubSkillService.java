package com.goodamcodes.service;
import com.goodamcodes.dto.SubSkillDTO;
import com.goodamcodes.mapper.SubSkillMapper;
import com.goodamcodes.model.Skill;
import com.goodamcodes.model.SubSkill;
import com.goodamcodes.repository.SkillRepository;
import com.goodamcodes.repository.SubSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SubSkillService {

    private final SubSkillRepository subSkillRepository;
    private final SkillRepository skillRepository;
    private final SubSkillMapper subSkillMapper;

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

    public List<SubSkillDTO> addAllSubSkills(List<SubSkillDTO> subSkillDTOs) {
        List<SubSkill> subSkills = subSkillMapper.toSubSkills(subSkillDTOs);

        Map<Long, Skill> skillMap = skillRepository.findAllById(
                subSkillDTOs.stream().map(SubSkillDTO::getSkillId).collect(Collectors.toSet())
        ).stream().collect(Collectors.toMap(Skill::getId, skill -> skill));

        List<SubSkill> newSubSkills = IntStream.range(0, subSkills.size())
                .filter(i -> subSkillRepository.findByName(subSkillDTOs.get(i).getName()).isEmpty())
                .mapToObj(i -> {
                    SubSkill subSkill = subSkills.get(i);
                    Long skillId = subSkillDTOs.get(i).getSkillId();
                    Skill skill = skillMap.get(skillId);
                    if (skill == null) {
                        throw new IllegalStateException("Skill with ID " + skillId + " not found");
                    }
                    subSkill.setSkill(skill);
                    return subSkill;
                })
                .collect(Collectors.toList());

        List<SubSkill> savedSubSkills = subSkillRepository.saveAll(newSubSkills);
        return subSkillMapper.toSubSkillDTOs(savedSubSkills);
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
