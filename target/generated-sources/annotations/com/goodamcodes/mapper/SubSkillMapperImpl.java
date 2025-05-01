package com.goodamcodes.mapper;

import com.goodamcodes.dto.SubSkillDTO;
import com.goodamcodes.model.SubSkill;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-01T18:50:32+0300",
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
    }
}
