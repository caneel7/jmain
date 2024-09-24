package com.caneel.jmain.service.implementations;

import com.caneel.jmain.dto.requests.UserRequestDto;
import com.caneel.jmain.dto.responses.ApiResponse;
import com.caneel.jmain.model.User;
import com.caneel.jmain.repository.UserRepository;
import com.caneel.jmain.service.AuthService;
import com.caneel.jmain.service.CustomUserDetailsService;
import com.caneel.jmain.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImplementation implements AuthService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public ResponseEntity<ApiResponse<User>> register(UserRequestDto data)
    {
        try{

            if (data.getEmail() == null || data.getPassword() == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Please Provide Email and Password"));
            }

            Optional<User> existingUser = userRepository.findByEmail(data.getEmail());
            if (existingUser.isPresent()) {
                return new ResponseEntity<>(new ApiResponse<>(false, "User Already Exists"), HttpStatus.CONFLICT);
            }

            User newUser = new User(data.getEmail(), passwordEncoder.encode(data.getPassword()));
            User savedUser = userRepository.save(newUser);

            return ResponseEntity.ok().body(new ApiResponse<>(true, "Success", savedUser));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(false,e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<User>> login(UserRequestDto data)
    {
        try{

            if(data.getEmail() == null) return ResponseEntity.badRequest().body(new ApiResponse<>(false,"Please Provide Email"));
            if(data.getPassword() == null) return ResponseEntity.badRequest().body(new ApiResponse<>(false,"Please Provide Password"));

            Optional<User> foundUser = userRepository.findByEmail(data.getEmail());

            //this should return 404 if user doesn't exist in db
            if(!foundUser.isPresent()) return ResponseEntity.badRequest().body(new ApiResponse<>(false,"Cannot Find User"));

            User user = foundUser.get();

            boolean passwordMatch = passwordEncoder.matches(data.getPassword(),user.getPassword());

            if(!passwordMatch) return new ResponseEntity<>(new ApiResponse<>(false,"Invalid Credentials"),HttpStatus.UNAUTHORIZED);

            String token = jwtService.generateToken(userDetailsService.loadUserById(user.getId()));

            user.setToken(token);

            return ResponseEntity.ok().body(new ApiResponse<>(true,"Logged In Successfully",user));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ApiResponse<>(false,e.getMessage()));
        }
    }
}
