package com.amanefer.crm.services.comment;

import com.amanefer.crm.dto.comment.CreateCommentDto;
import com.amanefer.crm.dto.task.TaskResponseDto;

public interface CommentService {

    TaskResponseDto addNewComment(String email, Integer id, CreateCommentDto dto);
}
