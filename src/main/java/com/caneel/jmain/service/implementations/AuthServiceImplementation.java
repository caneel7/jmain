package com.caneel.jmain.service.implementations;

import com.caneel.jmain.dto.requests.UserRequestDto;
import com.caneel.jmain.dto.responses.ApiResponse;
import com.caneel.jmain.model.User;
import com.caneel.jmain.repository.UserRepository;
import com.caneel.jmain.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImplementation implements AuthService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ApiResponse<User>> register(UserRequestDto data)
    {
        try{
            if(data.getEmail() == null) return ResponseEntity.badRequest().body(new ApiResponse<>(false,"Please Provide Email"));
            if(data.getPassword() == null) return ResponseEntity.badRequest().body(new ApiResponse<>(false,"Please Provide Password"));


            int duplicateUser = userRepository.countUserByEmail(data.getEmail());

            if(duplicateUser > 0){
                return new ResponseEntity<>(new ApiResponse<>(false,"User Already Exists"), HttpStatus.CONFLICT);
            }

            User newUser = userRepository.save(new User(data.getEmail(),passwordEncoder.encode(data.getPassword())));
            return ResponseEntity.ok().body(new ApiResponse<>(true,"Success",newUser));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(false,e.getMessage()));
        }
    }
}
