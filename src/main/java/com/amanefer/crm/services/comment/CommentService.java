package com.amanefer.crm.services.comment;

import com.amanefer.crm.entities.Comment;
import com.amanefer.crm.entities.Task;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentsByTask(Task task);

    Comment createComment(Comment comment);

    Comment updateComment(Comment comment);

    void deleteComment(Long id);
}
