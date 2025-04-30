package com.goodamcodes.dto;

import com.goodamcodes.dto.Skill.SkillRequestDTO;
import lombok.*;

@Data
public class SubSkillDTO {

    private String name;
    private int percentageLevel;
    private SkillRequestDTO skill;
}
