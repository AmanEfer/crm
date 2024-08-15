package com.amanefer.crm.repositories;

import com.amanefer.crm.entities.Comment;
import com.amanefer.crm.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTask(Task task);
}
