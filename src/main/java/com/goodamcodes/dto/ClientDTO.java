package com.goodamcodes.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientDTO {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String subject;
    private String message;

}
