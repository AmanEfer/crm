package com.amanefer.crm.dto.comment;

import lombok.Data;

@Data
public class CommentResponseDto {

    private Integer id;
    private Integer taskId;
    private Integer authorId;
    private String content;

}
