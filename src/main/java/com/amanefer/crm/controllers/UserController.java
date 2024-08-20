package com.amanefer.crm.controllers;

import com.amanefer.crm.dto.user.RegisterUserDto;
import com.amanefer.crm.dto.user.UserBasicFieldsDto;
import com.amanefer.crm.dto.user.UserResponseDto;
import com.amanefer.crm.mappers.UserMapper;
import com.amanefer.crm.services.auth.AuthenticationService;
import com.amanefer.crm.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authService;
    private final UserService userService;
    private final UserMapper userMapper;


    @Operation(summary = "Get all users")
    @GetMapping
    public List<UserBasicFieldsDto> getAllUsers() {
        return userMapper.fromEntityListToDtoList(userService.getAllUsers());
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Integer id) {
        return userMapper.fromEntityToDto(userService.getUserById(id));
    }

    @Operation(summary = "Get user by email")
    @GetMapping("/user")
    public UserResponseDto getUserByEmail(@RequestParam String email) {
        return userMapper.fromEntityToDto(userService.getUserByEmail(email));
    }

    @Operation(summary = "Create new user")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserBasicFieldsDto> createUser(@RequestBody RegisterUserDto dto) {
        UserBasicFieldsDto user = userMapper.fromUserToBasicFieldsDto(authService.registerNewUser(dto));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Update user")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Integer id, @RequestBody RegisterUserDto user) {
        return new ResponseEntity<>(userMapper.fromEntityToDto(userService.updateUser(id, user)), HttpStatus.OK);
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }
}
