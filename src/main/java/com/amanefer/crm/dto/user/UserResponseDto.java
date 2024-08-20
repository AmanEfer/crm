package com.amanefer.crm.dto.user;

import com.amanefer.crm.entities.Task;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {

    private Integer id;
    private String name;
    private String email;
    private List<Task> authoredTasks;
    private List<Task> assignedTasks;
}
