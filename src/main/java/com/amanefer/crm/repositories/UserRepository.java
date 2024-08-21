package com.amanefer.crm.repositories;

import com.amanefer.crm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserById(Integer id);

    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);
}
