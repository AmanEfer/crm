package com.amanefer.crm.services.user;

import com.amanefer.crm.dto.user.UserBasicFieldsDto;
import com.amanefer.crm.dto.user.UserRequestDto;
import com.amanefer.crm.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserBasicFieldsDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    UserResponseDto getUserByEmail(String email);

    UserBasicFieldsDto createUser(UserRequestDto user);

    UserResponseDto updateUser(Long id, UserRequestDto user);

    String deleteUser(Long id);
}
