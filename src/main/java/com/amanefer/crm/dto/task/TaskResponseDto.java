package com.amanefer.crm.dto.task;

import com.amanefer.crm.dto.comment.CommentResponseDto;
import com.amanefer.crm.states.TaskPriority;
import com.amanefer.crm.states.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {

    private Integer id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Integer authorId;
    private Integer assigneeId;
    private List<CommentResponseDto> comments;
}
