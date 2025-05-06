package com.goodamcodes.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SkillDTO {

    private Long id;
    private String name;
    private List<SubSkillDTO> subSkillList;
}
