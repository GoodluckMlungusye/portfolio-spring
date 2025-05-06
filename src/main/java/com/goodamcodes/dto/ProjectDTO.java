package com.goodamcodes.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProjectDTO {

    private Long id;
    private String name;
    private String technology;
    private double rate;
    private String repository;
    private String colorCode;
    private String image;
    private Boolean isHosted;
}
