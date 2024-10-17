package com.caneel.jmain.config;

import com.caneel.jmain.model.User;
import com.caneel.jmain.repository.UserRepository;
import com.caneel.jmain.service.CustomUserDetailsService;
import com.caneel.jmain.service.JWTService;
import org.springframework.beans.factory.annotation.Value;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Value("${ENDPOINT}")
    String ENDPOINT ;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String firstName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");

        Optional<User> user = userRepository.findByEmail(email);

        String token = "";

        if(user.isPresent()){
            token = jwtService.generateToken(userDetailsService.loadUserById(user.get().getId()));
        }else{
            User newUser = userRepository.save(new User(email,firstName,lastName));
            token = jwtService.generateToken(userDetailsService.loadUserById(newUser.getId()));
        }

        response.sendRedirect(ENDPOINT+"auth?token="+ token);

    }
}
