package com.goodamcodes.mapper;

import com.goodamcodes.dto.SubSkillDTO;
import com.goodamcodes.model.SubSkill;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubSkillMapper {

    @Mapping(target = "skill", ignore = true)
    SubSkill toSubSkill(SubSkillDTO subSkillDTO);
    @Mapping(target = "skillId", source = "skill.id")
    SubSkillDTO toSubSkillDTO(SubSkill subSkill);
    List<SubSkillDTO> toSubSkillDTOs(List<SubSkill> subSkills);
    List<SubSkill> toSubSkills(List<SubSkillDTO> skillDTOS);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSubSkillFromDTO(SubSkillDTO subSkillDTO, @MappingTarget SubSkill subSkill);
}
