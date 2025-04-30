package com.goodamcodes.mapper;

import com.goodamcodes.dto.Skill.SkillRequestDTO;
import com.goodamcodes.dto.Skill.SkillResponseDTO;
import com.goodamcodes.dto.SubSkillDTO;
import com.goodamcodes.model.Skill;
import com.goodamcodes.model.SubSkill;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-30T16:01:13+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class SkillMapperImpl implements SkillMapper {

    @Override
    public Skill toSkill(SkillRequestDTO skillRequestDTO) {
        if ( skillRequestDTO == null ) {
            return null;
        }

        Skill.SkillBuilder skill = Skill.builder();

        skill.name( skillRequestDTO.getName() );

        return skill.build();
    }

    @Override
    public SkillResponseDTO toSkillDTO(Skill skill) {
        if ( skill == null ) {
            return null;
        }

        SkillResponseDTO skillResponseDTO = new SkillResponseDTO();

        skillResponseDTO.setName( skill.getName() );
        skillResponseDTO.setSubSkillList( subSkillListToSubSkillDTOList( skill.getSubSkillList() ) );

        return skillResponseDTO;
    }

    @Override
    public List<SkillResponseDTO> toSkillDTOs(List<Skill> skills) {
        if ( skills == null ) {
            return null;
        }

        List<SkillResponseDTO> list = new ArrayList<SkillResponseDTO>( skills.size() );
        for ( Skill skill : skills ) {
            list.add( toSkillDTO( skill ) );
        }

        return list;
    }

    @Override
    public void updateSkillFromDTO(SkillRequestDTO skillRequestDTO, Skill skill) {
        if ( skillRequestDTO == null ) {
            return;
        }

        if ( skillRequestDTO.getName() != null ) {
            skill.setName( skillRequestDTO.getName() );
        }
    }

    protected SkillRequestDTO skillToSkillRequestDTO(Skill skill) {
        if ( skill == null ) {
            return null;
        }

        SkillRequestDTO skillRequestDTO = new SkillRequestDTO();

        skillRequestDTO.setName( skill.getName() );

        return skillRequestDTO;
    }

    protected SubSkillDTO subSkillToSubSkillDTO(SubSkill subSkill) {
        if ( subSkill == null ) {
            return null;
        }

        SubSkillDTO subSkillDTO = new SubSkillDTO();

        subSkillDTO.setName( subSkill.getName() );
        subSkillDTO.setPercentageLevel( subSkill.getPercentageLevel() );
        subSkillDTO.setSkill( skillToSkillRequestDTO( subSkill.getSkill() ) );

        return subSkillDTO;
    }

    protected List<SubSkillDTO> subSkillListToSubSkillDTOList(List<SubSkill> list) {
        if ( list == null ) {
            return null;
        }

        List<SubSkillDTO> list1 = new ArrayList<SubSkillDTO>( list.size() );
        for ( SubSkill subSkill : list ) {
            list1.add( subSkillToSubSkillDTO( subSkill ) );
        }

        return list1;
    }
}
