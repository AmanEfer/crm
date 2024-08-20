package com.amanefer.crm.services.user;

import com.amanefer.crm.dto.user.RegisterUserDto;
import com.amanefer.crm.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User getUserById(Integer id);

    User getUserByEmail(String email);

    User createUser(RegisterUserDto user);

    User updateUser(Integer id, RegisterUserDto user);

    String deleteUser(Integer id);
}
