package com.amanefer.crm.dto.comment;

import com.amanefer.crm.entities.Task;
import lombok.Data;

@Data
public class CommentResponseDto {

    private Integer id;
    private Task task;
//    private User author;
    private String content;
}
