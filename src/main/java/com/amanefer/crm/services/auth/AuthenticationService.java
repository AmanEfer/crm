package com.amanefer.crm.services.auth;

import com.amanefer.crm.dto.user.LoginUserDto;
import com.amanefer.crm.dto.user.RegisterUserDto;
import com.amanefer.crm.entities.User;
import com.amanefer.crm.security.JwtService;
import com.amanefer.crm.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User registerNewUser(RegisterUserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        return userService.createUser(dto);
    }

    public String authenticateUser(LoginUserDto dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        UserDetails user = userService.loadUserByUsername(dto.getEmail());

        return jwtService.generateToken(user);
    }
}
