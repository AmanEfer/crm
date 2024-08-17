package com.amanefer.crm.services.user;

import com.amanefer.crm.entities.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}
