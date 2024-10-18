package com.and.SchoolMgmt.controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.and.SchoolMgmt.dto.AuthenticationRequest;
import com.and.SchoolMgmt.dto.AuthenticationResponse;
import com.and.SchoolMgmt.utils.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationContoller {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/authenticate")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), 
                    authenticationRequest.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect Username or Password", e);
        } catch (DisabledException disabledException) {
            throw new DisabledException("User account is disabled", disabledException);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        
        return new AuthenticationResponse(jwt);
    }
}
