package com.amanefer.crm.repositories;

import com.amanefer.crm.entities.Task;
import com.amanefer.crm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByAuthor(User author);

    List<Task> findByAssignee(User assignee);

}
