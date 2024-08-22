package com.amanefer.crm.services.task;

import com.amanefer.crm.dto.task.TaskRequestDto;
import com.amanefer.crm.dto.task.TaskResponseAsPage;
import com.amanefer.crm.dto.task.TaskResponseDto;
import com.amanefer.crm.entities.Task;
import com.amanefer.crm.entities.User;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface TaskService {

    TaskResponseAsPage getAllUsersAllTasks(PageRequest pageRequest);

    TaskResponseAsPage getCurrentUserAllTasks(PageRequest pageRequest, String email);

    Task getTaskById(Integer id);

    List<Task> getTasksByAuthor(User author);

    List<Task> getTasksByAssignee(User assignee);

    TaskResponseDto createTask(TaskRequestDto dto, String email);

    TaskResponseDto updateTask(TaskRequestDto dto);

    void deleteTask(Integer id);
}
