package com.amanefer.crm.repositories;

import com.amanefer.crm.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("from Task t where t.author.id = :authorId")
    Page<Task> findByAuthor(Pageable pageable, Integer authorId);

    @Query("from Task t where t.assignee.id = :authorId")
    Page<Task> findByAssignee(Pageable pageable, Integer authorId);

    @Query("from Task t where t.assignee.email = :email")
    Page<Task> getUserAllTasks(Pageable pageable, String email);

}
