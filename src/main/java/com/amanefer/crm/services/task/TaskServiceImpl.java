package com.amanefer.crm.services.task;

import com.amanefer.crm.dto.common.ResponseDto;
import com.amanefer.crm.dto.task.TaskRequestDto;
import com.amanefer.crm.dto.task.TaskResponseAsPage;
import com.amanefer.crm.dto.task.TaskResponseDto;
import com.amanefer.crm.dto.task.UpdateTaskDto;
import com.amanefer.crm.entities.Task;
import com.amanefer.crm.entities.User;
import com.amanefer.crm.exceptions.TaskForbiddenOperationException;
import com.amanefer.crm.exceptions.TaskIllegalStateException;
import com.amanefer.crm.exceptions.TaskNotFoundException;
import com.amanefer.crm.mappers.TaskMapper;
import com.amanefer.crm.repositories.TaskRepository;
import com.amanefer.crm.services.user.UserService;
import com.amanefer.crm.states.TaskPriority;
import com.amanefer.crm.states.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    public static final String TASK_NOT_FOUND_FOUND = "Task with ID %d wasn't found";
    private static final String DELETE_NOT_ALLOWED_MESSAGE = "You can delete only your tasks";
    private static final String DELETE_SUCCESS_MESSAGE = "Task with ID %s was successfully deleted";
    private static final String UPDATE_NOT_ALLOWED_MESSAGE = "You can update only your tasks";
    private static final String INVALID_STATUS_MESSAGE = "Such status doesn't exists";
    private static final String INVALID_PRIORITY_MESSAGE = "Such priority doesn't exists";

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseAsPage getAllUsersAllTasks(Pageable pageable) {
        Page<Task> tasksPage = taskRepository.findAll(pageable);
        List<TaskResponseDto> content = getContent(tasksPage);

        return convertToTaskResponseAsPage(tasksPage, content);
    }

    @Override
    public TaskResponseAsPage getCurrentUserAllTasks(Pageable pageable, String email) {
        Page<Task> tasksPage = taskRepository.getUserAllTasks(pageable, email);
        List<TaskResponseDto> content = getContent(tasksPage);

        return convertToTaskResponseAsPage(tasksPage, content);
    }

    @Override
    public TaskResponseDto getTaskById(Integer id) {

        return taskMapper.fromEntityToDto(getTaskByIdAsEntity(id));
    }

    @Override
    public TaskResponseAsPage getTasksByAuthor(Pageable pageable, Integer authorId) {
        Page<Task> tasksPage = taskRepository.findByAuthor(pageable, authorId);
        List<TaskResponseDto> content = getContent(tasksPage);

        return convertToTaskResponseAsPage(tasksPage, content);
    }

    @Override
    public TaskResponseAsPage getTasksByAssignee(Pageable pageable, Integer assigneeId) {
        Page<Task> tasksPage = taskRepository.findByAssignee(pageable, assigneeId);
        List<TaskResponseDto> content = getContent(tasksPage);

        return convertToTaskResponseAsPage(tasksPage, content);    }

    @Override
    @Transactional
    public TaskResponseDto createTask(TaskRequestDto dto, String email) {
        User user = getUser(email);

        Task task = taskMapper.fromDtoToEntity(dto);
        task.setAuthor(user);
        task.setAssignee(user);
        task.setComments(new ArrayList<>());

        Task saved = taskRepository.save(task);

        return convertToTaskResponseDto(saved);
    }

    @Override
    @Transactional
    public TaskResponseDto updateTask(Integer id, String email, UpdateTaskDto dto) {
        Task task = getTaskByIdAsEntity(id);

        if (!task.getAssignee().getEmail().equals(email))
            throw new TaskForbiddenOperationException(UPDATE_NOT_ALLOWED_MESSAGE);

        if (dto.getTitle() != null && !dto.getTitle().isBlank())
            task.setTitle(dto.getTitle());

        if (dto.getDescription() != null && !dto.getDescription().isBlank())
            task.setDescription(dto.getDescription());

        return convertToTaskResponseDto(task);
    }

    @Override
    @Transactional
    public TaskResponseDto changeStatus(String email, Integer id, String status) {
        Task task = getTaskByIdAsEntity(id);

        if (!task.getAssignee().getEmail().equals(email))
            throw new TaskForbiddenOperationException(UPDATE_NOT_ALLOWED_MESSAGE);

        Set<String> statuses = Arrays.stream(TaskStatus.values())
                .map(TaskStatus::name)
                .collect(Collectors.toSet());

        if (!statuses.contains(status.toUpperCase()))
            throw new TaskIllegalStateException(INVALID_STATUS_MESSAGE);

        task.setStatus(TaskStatus.valueOf(status));

        return convertToTaskResponseDto(task);
    }

    @Override
    @Transactional
    public TaskResponseDto changePriority(String email, Integer id, String priority) {
        Task task = getTaskByIdAsEntity(id);

        if (!task.getAssignee().getEmail().equals(email))
            throw new TaskForbiddenOperationException(UPDATE_NOT_ALLOWED_MESSAGE);

        Set<String> priorities = Arrays.stream(TaskPriority.values())
                .map(TaskPriority::name)
                .collect(Collectors.toSet());

        if (!priorities.contains(priority.toUpperCase()))
            throw new TaskIllegalStateException(INVALID_PRIORITY_MESSAGE);

        task.setPriority(TaskPriority.valueOf(priority));

        return convertToTaskResponseDto(task);
    }

    @Override
    @Transactional
    public TaskResponseDto changeAssignee(String email, Integer id, Integer assigneeId) {
        Task task = getTaskByIdAsEntity(id);

        if (!task.getAuthor().getEmail().equals(email) && !task.getAssignee().getEmail().equals(email))
            throw new TaskForbiddenOperationException(UPDATE_NOT_ALLOWED_MESSAGE);

        task.setAssignee(userService.getUserByIdAsEntity(assigneeId));

        return convertToTaskResponseDto(task);
    }

    @Override
    @Transactional
    public ResponseDto deleteTask(int id, String email) {
        Task task = getTaskByIdAsEntity(id);

        if (!task.getAssignee().getEmail().equals(email))
            throw new TaskForbiddenOperationException(DELETE_NOT_ALLOWED_MESSAGE);

        taskRepository.deleteById(id);

        return new ResponseDto(String.format(DELETE_SUCCESS_MESSAGE, id));

    }

    private Task getTaskByIdAsEntity(Integer id) {

        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format(TASK_NOT_FOUND_FOUND, id)));
    }

    private User getUser(String email) {

        return userService.getUserByEmailAsEntity(email);
    }

    private TaskResponseDto convertToTaskResponseDto(Task task) {
        TaskResponseDto dto = taskMapper.fromEntityToDto(task);
        dto.setAuthorId(task.getAuthor().getId());
        dto.setAssigneeId(task.getAssignee().getId());

        return dto;
    }

    private static TaskResponseAsPage convertToTaskResponseAsPage(Page<Task> tasksPage, List<TaskResponseDto> content) {

        return TaskResponseAsPage.builder()
                .tasks(content)
                .pageNumber(tasksPage.getNumber())
                .pagesCount(tasksPage.getTotalPages())
                .build();
    }

    private List<TaskResponseDto> getContent(Page<Task> tasksPage) {

        return tasksPage.getContent().stream()
                .map(this::convertToTaskResponseDto)
                .toList();
    }

}
