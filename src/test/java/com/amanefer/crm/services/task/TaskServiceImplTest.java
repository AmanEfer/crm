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
import com.amanefer.crm.states.TaskPriority;
import com.amanefer.crm.states.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    public static final String EMAIL = "test@gmail.com";
    public static final int USER_ID = 1;
    public static final int NOT_EXISTS_TASK_ID = 0;
    public static final String WRONG_EMAIL = "wrong@gmail.com";
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Pageable pageable;

    private Task expectedTask1;
    private Task expectedTask2;
    private Task expectedTask3;
    private TaskResponseDto expectedTaskDto1;
    private TaskResponseDto expectedTaskDto2;
    private TaskResponseDto expectedTaskDto3;
    private List<Task> tasks;
    private List<TaskResponseDto> tasksDto;
    private TaskRequestDto taskRequestDto;
    private TaskResponseAsPage expectedResponseAsPage;
    private Page<Task> expectedTasksPage;
    private User user;


    @BeforeEach
    void init() {

        user = User.builder()
                .id(1)
                .name("user")
                .password("password")
                .email(EMAIL)
                .authoredTasks(Collections.emptySet())
                .assignedTasks(Collections.emptySet())
                .roles(Collections.emptySet())
                .build();

        pageable = PageRequest.of(0, 1);

        expectedTask1 = Task.builder()
                .id(1)
                .title("title1")
                .description("description1")
                .status(TaskStatus.IN_WAITING)
                .priority(TaskPriority.MEDIUM)
                .author(user)
                .assignee(user)
                .comments(Collections.emptyList())
                .build();

        expectedTask2 = Task.builder()
                .id(2)
                .title("title2")
                .description("description2")
                .status(TaskStatus.IN_WAITING)
                .priority(TaskPriority.MEDIUM)
                .author(user)
                .assignee(user)
                .comments(Collections.emptyList())
                .build();

        expectedTask3 = Task.builder()
                .id(3)
                .title("title3")
                .description("description3")
                .status(TaskStatus.IN_WAITING)
                .priority(TaskPriority.MEDIUM)
                .author(user)
                .assignee(user)
                .comments(Collections.emptyList())
                .build();

        expectedTaskDto1 = TaskResponseDto.builder()
                .id(expectedTask1.getId())
                .title(expectedTask1.getTitle())
                .description(expectedTask1.getDescription())
                .status(expectedTask1.getStatus())
                .priority(expectedTask1.getPriority())
                .authorId(expectedTask1.getAuthor().getId())
                .assigneeId(expectedTask1.getAssignee().getId())
                .comments(Collections.emptyList())
                .build();

        expectedTaskDto2 = TaskResponseDto.builder()
                .id(expectedTask2.getId())
                .title(expectedTask2.getTitle())
                .description(expectedTask2.getDescription())
                .status(expectedTask2.getStatus())
                .priority(expectedTask2.getPriority())
                .authorId(expectedTask2.getAuthor().getId())
                .assigneeId(expectedTask2.getAssignee().getId())
                .comments(Collections.emptyList())
                .build();

        expectedTaskDto3 = TaskResponseDto.builder()
                .id(expectedTask3.getId())
                .title(expectedTask3.getTitle())
                .description(expectedTask3.getDescription())
                .status(expectedTask3.getStatus())
                .priority(expectedTask3.getPriority())
                .authorId(expectedTask3.getAuthor().getId())
                .assigneeId(expectedTask3.getAssignee().getId())
                .comments(Collections.emptyList())
                .build();

        tasks = List.of(expectedTask1, expectedTask2, expectedTask3);
        tasksDto = List.of(expectedTaskDto1, expectedTaskDto2, expectedTaskDto3);

        expectedResponseAsPage = TaskResponseAsPage.builder()
                .tasks(tasksDto)
                .pageNumber(pageable.getPageNumber())
                .pagesCount(pageable.getPageSize())
                .build();

        expectedTasksPage = new PageImpl<>(tasks, pageable, tasks.size());

        taskRequestDto = TaskRequestDto.builder()
                .title("title1")
                .description("description1")
                .build();
    }


    @Test
    void getAllUsersAllTasks() {
        when(taskRepository.findAll(pageable)).thenReturn(expectedTasksPage);
        when(taskMapper.fromPageToTaskResponseAsPage(expectedTasksPage)).thenReturn(expectedResponseAsPage);

        TaskResponseAsPage actualResponseAsPage = taskService.getAllUsersAllTasks(pageable);

        assertAll(() -> {
            assertNotNull(actualResponseAsPage);
            assertEquals(3, actualResponseAsPage.getTasks().size());
            assertEquals(expectedResponseAsPage, actualResponseAsPage);
            verify(taskRepository, times(1)).findAll(pageable);
            verify(taskMapper, times(1)).fromPageToTaskResponseAsPage(expectedTasksPage);
        });
    }

    @Test
    void getCurrentUserAllTasks() {
        when(taskRepository.getUserAllTasks(pageable, EMAIL)).thenReturn(expectedTasksPage);
        when(taskMapper.fromPageToTaskResponseAsPage(expectedTasksPage)).thenReturn(expectedResponseAsPage);

        TaskResponseAsPage actualResponseAsPage = taskService.getCurrentUserAllTasks(pageable, EMAIL);

        assertAll(() -> {
            assertNotNull(actualResponseAsPage);
            assertEquals(3, actualResponseAsPage.getTasks().size());
            assertEquals(expectedResponseAsPage, actualResponseAsPage);
            verify(taskRepository, times(1)).getUserAllTasks(pageable, EMAIL);
            verify(taskMapper, times(1)).fromPageToTaskResponseAsPage(expectedTasksPage);
        });
    }

    @Test
    void getTaskByIdAsEntity() {
        when(taskRepository.findById(expectedTask1.getId())).thenReturn(Optional.of(expectedTask1));

        Task actualTask = taskService.getTaskByIdAsEntity(1);

        assertAll(() -> {
            assertNotNull(actualTask);
            assertEquals(expectedTask1, actualTask);
            verify(taskRepository, times(1)).findById(expectedTask1.getId());
        });
    }

    @Test
    void getTaskByIdAsEntity_throwsException() {
        when(taskRepository.findById(NOT_EXISTS_TASK_ID)).thenReturn(Optional.empty());

        assertAll(() -> {
            assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(NOT_EXISTS_TASK_ID));
            verify(taskRepository, times(1)).findById(NOT_EXISTS_TASK_ID);
        });
    }

    @Test
    void getTaskById() {
        when(taskRepository.findById(expectedTask1.getId())).thenReturn(Optional.of(expectedTask1));
        when(taskMapper.toDto(expectedTask1)).thenReturn(expectedTaskDto1);

        TaskResponseDto actualTaskDto = taskService.getTaskById(expectedTask1.getId());

        assertAll(() -> {
            assertNotNull(actualTaskDto);
            assertEquals(expectedTaskDto1, actualTaskDto);
            verify(taskRepository, times(1)).findById(expectedTask1.getId());
            verify(taskMapper, times(1)).toDto(expectedTask1);
        });
    }

    @Test
    void getTasksByAuthor() {
        when(taskRepository.findByAuthor(pageable, USER_ID)).thenReturn(expectedTasksPage);
        when(taskMapper.fromPageToTaskResponseAsPage(expectedTasksPage)).thenReturn(expectedResponseAsPage);

        TaskResponseAsPage actualResponseAsPage = taskService.getTasksByAuthor(pageable, USER_ID);

        assertAll(() -> {
            assertNotNull(actualResponseAsPage);
            assertEquals(3, actualResponseAsPage.getTasks().size());
            assertEquals(expectedResponseAsPage, actualResponseAsPage);
            verify(taskRepository, times(1)).findByAuthor(pageable, USER_ID);
            verify(taskMapper, times(1)).fromPageToTaskResponseAsPage(expectedTasksPage);
        });
    }

    @Test
    void getTasksByAssignee() {
        when(taskRepository.findByAssignee(pageable, USER_ID)).thenReturn(expectedTasksPage);
        when(taskMapper.fromPageToTaskResponseAsPage(expectedTasksPage)).thenReturn(expectedResponseAsPage);

        TaskResponseAsPage actualResponseAsPage = taskService.getTasksByAssignee(pageable, USER_ID);

        assertAll(() -> {
            assertNotNull(actualResponseAsPage);
            assertEquals(3, actualResponseAsPage.getTasks().size());
            assertEquals(expectedResponseAsPage, actualResponseAsPage);
            verify(taskRepository, times(1)).findByAssignee(pageable, USER_ID);
            verify(taskMapper, times(1)).fromPageToTaskResponseAsPage(expectedTasksPage);
        });
    }

    @Test
    void createTask() {
        Task mappedTask = Task.builder()
                .title(taskRequestDto.getTitle())
                .description(taskRequestDto.getDescription())
                .build();

        when(userService.getUserByEmailAsEntity(EMAIL)).thenReturn(user);
        when(taskMapper.toEntity(taskRequestDto)).thenReturn(mappedTask);
        when(taskRepository.save(mappedTask)).thenReturn(expectedTask1);
        when(taskMapper.toDto(expectedTask1)).thenReturn(expectedTaskDto1);

        TaskResponseDto actualResponseDto = taskService.createTask(taskRequestDto, EMAIL);

        assertAll(() -> {
            assertNotNull(actualResponseDto);
            assertEquals(expectedTaskDto1, actualResponseDto);
            verify(userService, times(1)).getUserByEmailAsEntity(EMAIL);
            verify(taskMapper, times(1)).toEntity(taskRequestDto);
            verify(taskRepository, times(1)).save(mappedTask);
            verify(taskMapper, times(1)).toDto(expectedTask1);
        });
    }

    @Test
    void updateTask() {
        when(taskRepository.findById(expectedTask1.getId())).thenReturn(Optional.of(expectedTask1));
        when(taskMapper.toDto(expectedTask1)).thenReturn(expectedTaskDto1);

        TaskResponseDto actualResponseDto = taskService.updateTask(USER_ID, EMAIL, taskRequestDto);

        assertAll(() -> {
            assertNotNull(actualResponseDto);
            assertEquals(expectedTaskDto1, actualResponseDto);
        });
    }

    @Test
    void updateTask_throwsTaskForbiddenOperationException() {
        when(taskRepository.findById(expectedTask1.getId())).thenReturn(Optional.of(expectedTask1));

        assertAll(() -> {
            assertThrows(TaskForbiddenOperationException.class,
                    () -> taskService.updateTask(USER_ID, WRONG_EMAIL, taskRequestDto));
            verify(taskRepository, times(1)).findById(expectedTask1.getId());
        });
    }
}






