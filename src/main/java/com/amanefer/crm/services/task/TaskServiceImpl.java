package com.amanefer.crm.services.task;

import com.amanefer.crm.dto.task.TaskRequestDto;
import com.amanefer.crm.dto.task.TaskResponseAsPage;
import com.amanefer.crm.dto.task.TaskResponseDto;
import com.amanefer.crm.entities.Task;
import com.amanefer.crm.entities.User;
import com.amanefer.crm.exceptions.TaskForbiddenOperationException;
import com.amanefer.crm.exceptions.TaskNotFoundException;
import com.amanefer.crm.mappers.TaskMapper;
import com.amanefer.crm.repositories.TaskRepository;
import com.amanefer.crm.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private static final String DELETE_NOT_ALLOWED_MESSAGE = "You can delete only your tasks";

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseAsPage getAllUsersAllTasks(PageRequest pageRequest) {
        Page<Task> tasksPage = taskRepository.findAll(pageRequest);
        List<TaskResponseDto> content = getContent(tasksPage);

        return convertToTaskResponseAsPage(tasksPage, content);
    }

    @Override
    public TaskResponseAsPage getCurrentUserAllTasks(PageRequest pageRequest, String email) {
        Page<Task> tasksPage = taskRepository.getUserAllTasks(pageRequest, email);
        List<TaskResponseDto> content = getContent(tasksPage);

        return convertToTaskResponseAsPage(tasksPage, content);
    }

    @Override
    public Task getTaskById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with ID %d wasn't found", id)));
    }

    @Override
    public List<Task> getTasksByAuthor(User author) {
        return taskRepository.findByAuthor(author);
    }

    @Override
    public List<Task> getTasksByAssignee(User assignee) {
        return taskRepository.findByAssignee(assignee);
    }

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
    public TaskResponseDto updateTask(TaskRequestDto task) {
        return null;
    }

    @Override
    @Transactional
    public void deleteTask(int id, String email) {
        Task task = getTaskById(id);

        if (!task.getAssignee().getEmail().equals(email))
            throw new TaskForbiddenOperationException(DELETE_NOT_ALLOWED_MESSAGE);


        taskRepository.deleteById(id);
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
