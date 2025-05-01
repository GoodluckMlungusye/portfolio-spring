package com.goodamcodes.mapper;

import com.goodamcodes.dto.SkillDTO;
import com.goodamcodes.dto.SubSkillDTO;
import com.goodamcodes.model.Skill;
import com.goodamcodes.model.SubSkill;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-01T18:50:32+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class SkillMapperImpl implements SkillMapper {

    @Autowired
    private SubSkillMapper subSkillMapper;

    @Override
    public Skill toSkill(SkillDTO skillDTO) {
        if ( skillDTO == null ) {
            return null;
        }

        Skill.SkillBuilder skill = Skill.builder();

        skill.name( skillDTO.getName() );
        skill.subSkillList( subSkillDTOListToSubSkillList( skillDTO.getSubSkillList() ) );

        return skill.build();
    }

    @Override
    public SkillDTO toSkillDTO(Skill skill) {
        if ( skill == null ) {
            return null;
        }

        SkillDTO skillDTO = new SkillDTO();

        skillDTO.setName( skill.getName() );
        skillDTO.setSubSkillList( subSkillMapper.toSubSkillDTOs( skill.getSubSkillList() ) );

        return skillDTO;
    }

    @Override
    public List<SkillDTO> toSkillDTOs(List<Skill> skills) {
        if ( skills == null ) {
            return null;
        }

        List<SkillDTO> list = new ArrayList<SkillDTO>( skills.size() );
        for ( Skill skill : skills ) {
            list.add( toSkillDTO( skill ) );
        }

        return list;
    }

    @Override
    public void updateSkillFromDTO(SkillDTO skillDTO, Skill skill) {
        if ( skillDTO == null ) {
            return;
        }

        if ( skillDTO.getName() != null ) {
            skill.setName( skillDTO.getName() );
        }
        if ( skill.getSubSkillList() != null ) {
            List<SubSkill> list = subSkillDTOListToSubSkillList( skillDTO.getSubSkillList() );
            if ( list != null ) {
                skill.getSubSkillList().clear();
                skill.getSubSkillList().addAll( list );
            }
        }
        else {
            List<SubSkill> list = subSkillDTOListToSubSkillList( skillDTO.getSubSkillList() );
            if ( list != null ) {
                skill.setSubSkillList( list );
            }
        }
    }

    protected List<SubSkill> subSkillDTOListToSubSkillList(List<SubSkillDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<SubSkill> list1 = new ArrayList<SubSkill>( list.size() );
        for ( SubSkillDTO subSkillDTO : list ) {
            list1.add( subSkillMapper.toSubSkill( subSkillDTO ) );
        }

        return list1;
    }
}
