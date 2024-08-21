package com.amanefer.crm.services.user;

import com.amanefer.crm.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User getUserById(Integer id);

    User getUserByEmail(String email);

    User createUser(User user);

    User updateUser(Integer id, User user);

    String deleteUser(Integer id);
}
