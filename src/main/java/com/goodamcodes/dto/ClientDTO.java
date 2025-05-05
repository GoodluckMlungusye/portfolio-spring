package com.goodamcodes.dto;

import lombok.Data;

@Data
public class ClientDTO {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String subject;
    private String message;

}
