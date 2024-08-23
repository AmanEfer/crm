package com.amanefer.crm.services.task;

import com.amanefer.crm.dto.common.ResponseDto;
import com.amanefer.crm.dto.task.TaskRequestDto;
import com.amanefer.crm.dto.task.TaskResponseAsPage;
import com.amanefer.crm.dto.task.TaskResponseDto;
import com.amanefer.crm.dto.task.UpdateTaskDto;
import com.amanefer.crm.entities.Task;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    TaskResponseAsPage getAllUsersAllTasks(Pageable pageable);

    TaskResponseAsPage getCurrentUserAllTasks(Pageable pageable, String email);

    TaskResponseDto getTaskById(Integer id);

    Task getTaskByIdAsEntity(Integer id);

    TaskResponseAsPage getTasksByAuthor(Pageable pageable, Integer authorId);

    TaskResponseAsPage getTasksByAssignee(Pageable pageable, Integer assigneeId);

    TaskResponseDto createTask(TaskRequestDto dto, String email);

    TaskResponseDto updateTask(Integer id, String email, UpdateTaskDto dto);

    TaskResponseDto changeStatus(String email, Integer id, String status);

    ResponseDto deleteTask(int id, String email);

    TaskResponseDto changePriority(String email, Integer id, String priority);

    TaskResponseDto changeAssignee(String email, Integer id, Integer assigneeId);

}
