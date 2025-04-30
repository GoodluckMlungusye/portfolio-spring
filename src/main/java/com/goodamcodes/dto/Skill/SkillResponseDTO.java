package com.goodamcodes.dto.Skill;

import com.goodamcodes.dto.SubSkillDTO;
import lombok.*;

import java.util.List;

@Data
public class SkillResponseDTO {

    private String name;
    private List<SubSkillDTO> subSkillList;
}
