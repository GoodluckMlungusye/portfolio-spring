package com.goodamcodes.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SubSkillDTO {

    private String name;
    private int percentageLevel;
    private Long skillId;
}
