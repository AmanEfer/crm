package com.amanefer.crm.dto.comment;

import com.amanefer.crm.entities.Task;
import lombok.Data;

@Data
public class CommentRequestDto {

    private Task task;
//    private User author;
    private String content;
}
