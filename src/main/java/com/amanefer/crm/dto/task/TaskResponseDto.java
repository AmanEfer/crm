package com.amanefer.crm.dto.task;

import com.amanefer.crm.entities.Comment;
import com.amanefer.crm.states.TaskPriority;
import com.amanefer.crm.states.TaskStatus;
import lombok.Data;

import java.util.List;

@Data
public class TaskResponseDto {

    private Integer id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Integer authorId;
    private Integer assigneeId;
    private List<Comment> comments;
}
