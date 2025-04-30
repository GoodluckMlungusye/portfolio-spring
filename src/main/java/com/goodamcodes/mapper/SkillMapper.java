package com.goodamcodes.mapper;

import com.goodamcodes.dto.Skill.SkillRequestDTO;
import com.goodamcodes.dto.Skill.SkillResponseDTO;
import com.goodamcodes.model.Skill;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    Skill toSkill(SkillRequestDTO skillRequestDTO);
    SkillResponseDTO toSkillDTO(Skill skill);
    List<SkillResponseDTO> toSkillDTOs(List<Skill> skills);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSkillFromDTO(SkillRequestDTO skillRequestDTO, @MappingTarget Skill skill);
}
