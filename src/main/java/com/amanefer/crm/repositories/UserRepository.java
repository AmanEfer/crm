package com.amanefer.crm.repositories;

import com.amanefer.crm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            from User u
            left join fetch u.authoredTasks
            left join fetch u.assignedTasks
            where u.id = :id
                        """)
    Optional<User> findUserById(Long id);

    @Query("""
            from User u
            left join fetch u.authoredTasks
            left join fetch u.assignedTasks
            where u.email = :email
            """)
    Optional<User> findUserByEmail(String email);

}
