package com.amanefer.crm.repositories;

import com.amanefer.crm.entities.Task;
import com.amanefer.crm.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByAuthor(User author);

    List<Task> findByAssignee(User assignee);

    @Query("from Task t where t.assignee.email = :email")
    Page<Task> getUserAllTasks(Pageable pageable, String email);

}
