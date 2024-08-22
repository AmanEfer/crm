package com.amanefer.crm.services.user;

import com.amanefer.crm.dto.user.RegisterUserDto;
import com.amanefer.crm.dto.user.UpdateUserDto;
import com.amanefer.crm.dto.user.UserBasicFieldsDto;
import com.amanefer.crm.dto.user.UserResponseDto;
import com.amanefer.crm.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserBasicFieldsDto> getAllUsers();

    UserResponseDto getUserById(Integer id);

    UserResponseDto getUserByEmail(String email);

    User getUserByEmailAsEntity(String email);

    UserBasicFieldsDto createUser(RegisterUserDto dto);

    UserResponseDto updateUser(Integer id, UpdateUserDto dto);

    String deleteUser(Integer id);
}
