package com.amanefer.crm.repositories;

import com.amanefer.crm.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
