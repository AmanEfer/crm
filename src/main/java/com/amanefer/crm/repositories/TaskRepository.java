package com.amanefer.crm.repositories;

import com.amanefer.crm.entities.Task;
import com.amanefer.crm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAuthor(User author);

    List<Task> findByAssignee(User assignee);

    Optional<Task> findByTitle(String title);
}
