package com.amanefer.crm.services.user;

import com.amanefer.crm.entities.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);
}
