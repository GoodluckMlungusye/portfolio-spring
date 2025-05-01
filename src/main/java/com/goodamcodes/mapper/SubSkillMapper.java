package com.goodamcodes.mapper;

import com.goodamcodes.dto.SubSkillDTO;
import com.goodamcodes.model.SubSkill;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubSkillMapper {

    @Mapping(target = "skill", ignore = true)
    SubSkill toSubSkill(SubSkillDTO subSkillDTO);
    SubSkillDTO toSubSkillDTO(SubSkill subSkill);
    List<SubSkillDTO> toSubSkillDTOs(List<SubSkill> subSkills);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSubSkillFromDTO(SubSkillDTO subSkillDTO, @MappingTarget SubSkill subSkill);
}
