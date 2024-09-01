package com.caneel.jmain.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.rmi.ServerException;

@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServerException
    {

        System.out.println("Authentication Failure : " + exception.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    }
}
