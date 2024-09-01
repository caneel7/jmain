package com.caneel.jmain.service;

import com.caneel.jmain.dto.requests.UserRequestDto;
import com.caneel.jmain.dto.responses.ApiResponse;
import com.caneel.jmain.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ApiResponse<User>> register(UserRequestDto data);

    ResponseEntity<ApiResponse<User>> login(UserRequestDto data);
}
