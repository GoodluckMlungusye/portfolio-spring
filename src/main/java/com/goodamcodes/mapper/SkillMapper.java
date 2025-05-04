package com.goodamcodes.mapper;

import com.goodamcodes.dto.SkillDTO;
import com.goodamcodes.model.Skill;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = SubSkillMapper.class)
public interface SkillMapper {
    Skill toSkill(SkillDTO skillDTO);
    SkillDTO toSkillDTO(Skill skill);
    List<SkillDTO> toSkillDTOs(List<Skill> skills);
    List<Skill> toSkills(List<SkillDTO> skillDTOS);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSkillFromDTO(SkillDTO skillDTO, @MappingTarget Skill skill);
}
