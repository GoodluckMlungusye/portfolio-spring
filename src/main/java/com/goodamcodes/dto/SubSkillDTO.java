package com.goodamcodes.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubSkillDTO {

    private Long id;
    private String name;
    private int percentageLevel;
    private Long skillId;
}
