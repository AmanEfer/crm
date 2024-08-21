package com.amanefer.crm.dto.task;

import com.amanefer.crm.states.TaskPriority;
import com.amanefer.crm.states.TaskStatus;
import lombok.Data;

@Data
public class TaskRequestDto {

    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
}
