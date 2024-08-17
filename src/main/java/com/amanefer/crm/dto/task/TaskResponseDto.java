package com.amanefer.crm.dto.task;

import com.amanefer.crm.entities.Comment;
import com.amanefer.crm.entities.User;
import com.amanefer.crm.states.TaskPriority;
import com.amanefer.crm.states.TaskStatus;
import lombok.Data;

import java.util.List;

@Data
public class TaskResponseDto {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private User author;
    private User assignee;
    private List<Comment> comments;
}
