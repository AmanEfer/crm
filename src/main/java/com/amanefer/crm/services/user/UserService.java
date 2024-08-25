package com.amanefer.crm.services.user;

import com.amanefer.crm.dto.common.ResponseDto;
import com.amanefer.crm.dto.user.RegisterUserDto;
import com.amanefer.crm.dto.user.UpdateUserDto;
import com.amanefer.crm.dto.user.UserResponseDto;
import com.amanefer.crm.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Integer id);

    User getUserByIdAsEntity(Integer id);

    UserResponseDto getUserByEmail(String email);

    User getUserByEmailAsEntity(String email);

    UserResponseDto createUser(RegisterUserDto dto);

    UserResponseDto updateUser(Integer id, UpdateUserDto dto);

    ResponseDto deleteUser(Integer id);

}
