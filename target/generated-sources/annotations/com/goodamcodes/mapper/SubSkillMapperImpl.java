package com.goodamcodes.mapper;

import com.goodamcodes.dto.Skill.SkillRequestDTO;
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
public class SubSkillMapperImpl implements SubSkillMapper {

    @Override
    public SubSkill toSubSkill(SubSkillDTO subSkillDTO) {
        if ( subSkillDTO == null ) {
            return null;
        }

        SubSkill.SubSkillBuilder subSkill = SubSkill.builder();

        subSkill.name( subSkillDTO.getName() );
        subSkill.percentageLevel( subSkillDTO.getPercentageLevel() );

        return subSkill.build();
    }

    @Override
    public SubSkillDTO toSubSkillDTO(SubSkill subSkill) {
        if ( subSkill == null ) {
            return null;
        }

        SubSkillDTO subSkillDTO = new SubSkillDTO();

        subSkillDTO.setName( subSkill.getName() );
        subSkillDTO.setPercentageLevel( subSkill.getPercentageLevel() );
        subSkillDTO.setSkill( skillToSkillRequestDTO( subSkill.getSkill() ) );

        return subSkillDTO;
    }

    @Override
    public List<SubSkillDTO> toSubSkillDTOs(List<SubSkill> subSkills) {
        if ( subSkills == null ) {
            return null;
        }

        List<SubSkillDTO> list = new ArrayList<SubSkillDTO>( subSkills.size() );
        for ( SubSkill subSkill : subSkills ) {
            list.add( toSubSkillDTO( subSkill ) );
        }

        return list;
    }

    @Override
    public void updateSubSkillFromDTO(SubSkillDTO subSkillDTO, SubSkill subSkill) {
        if ( subSkillDTO == null ) {
            return;
        }

        if ( subSkillDTO.getName() != null ) {
            subSkill.setName( subSkillDTO.getName() );
        }
        subSkill.setPercentageLevel( subSkillDTO.getPercentageLevel() );
        if ( subSkillDTO.getSkill() != null ) {
            if ( subSkill.getSkill() == null ) {
                subSkill.setSkill( Skill.builder().build() );
            }
            skillRequestDTOToSkill( subSkillDTO.getSkill(), subSkill.getSkill() );
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

    protected void skillRequestDTOToSkill(SkillRequestDTO skillRequestDTO, Skill mappingTarget) {
        if ( skillRequestDTO == null ) {
            return;
        }

        if ( skillRequestDTO.getName() != null ) {
            mappingTarget.setName( skillRequestDTO.getName() );
        }
    }
}
