package com.goodamcodes.dto;

import lombok.*;

@Data
public class ProjectDTO {

    private String name;
    private String technology;
    private double rate;
    private String repository;
    private String colorCode;
    private String image;
    private Boolean isHosted;
}
