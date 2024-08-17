package com.amanefer.crm.services.user;

import com.amanefer.crm.dto.user.UserRequestDto;
import com.amanefer.crm.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    UserResponseDto getUserByEmail(String email);

    UserResponseDto createUser(UserRequestDto user);

    UserResponseDto updateUser(Long id, UserRequestDto user);

    void deleteUser(Long id);
}
