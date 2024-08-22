package com.amanefer.crm.controllers;

import com.amanefer.crm.dto.user.LoginUserDto;
import com.amanefer.crm.dto.user.RegisterUserDto;
import com.amanefer.crm.dto.user.UserBasicFieldsDto;
import com.amanefer.crm.services.auth.AuthenticationService;
import com.amanefer.crm.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserBasicFieldsDto> registration(@RequestBody RegisterUserDto dto) {

        return new ResponseEntity<>(userService.createUser(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDto dto) {

        return new ResponseEntity<>(authService.authenticateUser(dto), HttpStatus.OK);
    }

}
