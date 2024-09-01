package com.caneel.jmain.dto.requests;

import lombok.Data;

@Data
public class UserRequestDto {

    private String id;
    private String email;
    private String mobile;
    private String password;

}
