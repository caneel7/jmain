package com.caneel.jmain.controller;

import com.caneel.jmain.dto.requests.UserRequestDto;
import com.caneel.jmain.dto.responses.ApiResponse;
import com.caneel.jmain.model.User;
import com.caneel.jmain.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = "application/json")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody UserRequestDto data)
    {
       return authService.register(data);
    }

}
