package com.amanefer.crm.controllers;

import com.amanefer.crm.dto.common.ResponseDto;
import com.amanefer.crm.dto.task.TaskRequestDto;
import com.amanefer.crm.dto.task.TaskResponseAsPage;
import com.amanefer.crm.dto.task.TaskResponseDto;
import com.amanefer.crm.dto.task.UpdateTaskDto;
import com.amanefer.crm.services.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<TaskResponseAsPage> getAllTasks(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "isOnlyMy", required = false, defaultValue = "true") boolean isOnlyMy) {

        TaskResponseAsPage response;

        if (isOnlyMy)
            response = taskService.getCurrentUserAllTasks(PageRequest.of(page, size), user.getUsername());
        else
            response = taskService.getAllUsersAllTasks(PageRequest.of(page, size));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Integer id) {

        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @GetMapping("/author/{id}")
    public TaskResponseAsPage getTasksByAuthor(@PathVariable("id") Integer authorId,
                                               @RequestParam(required = false, defaultValue = "0") Integer page,
                                               @RequestParam(required = false, defaultValue = "3") Integer size) {

        return taskService.getTasksByAuthor(PageRequest.of(page, size), authorId);
    }

    @GetMapping("/assignee/{id}")
    public TaskResponseAsPage getTasksByAssignee(@PathVariable("id") Integer assigneeId,
                                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "3") Integer size) {

        return taskService.getTasksByAssignee(PageRequest.of(page, size), assigneeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskResponseDto> createTask(@AuthenticationPrincipal UserDetails user,
                                                      @RequestBody TaskRequestDto dto) {

        return new ResponseEntity<>(taskService.createTask(dto, user.getUsername()), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@AuthenticationPrincipal UserDetails user,
                                                      @PathVariable Integer id,
                                                      @RequestBody UpdateTaskDto dto) {

        return new ResponseEntity<>(taskService.updateTask(id, user.getUsername(), dto), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDto> changeStatus(@AuthenticationPrincipal UserDetails user,
                                                        @PathVariable Integer id,
                                                        @RequestParam String status) {

        return new ResponseEntity<>(taskService.changeStatus(user.getUsername(), id, status), HttpStatus.OK);
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<TaskResponseDto> changePriority(@AuthenticationPrincipal UserDetails user,
                                                          @PathVariable Integer id,
                                                          @RequestParam String priority) {

        return new ResponseEntity<>(taskService.changePriority(user.getUsername(), id, priority), HttpStatus.OK);
    }

    @PatchMapping("/{id}/assignee")
    public ResponseEntity<TaskResponseDto> changeAssignee(@AuthenticationPrincipal UserDetails user,
                                                          @PathVariable Integer id,
                                                          @RequestParam("assignee_id") Integer assigneeId) {

        return new ResponseEntity<>(taskService.changeAssignee(user.getUsername(), id, assigneeId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteTask(@AuthenticationPrincipal UserDetails user,
                                                  @PathVariable int id) {

        return new ResponseEntity<>(taskService.deleteTask(id, user.getUsername()), HttpStatus.OK);
    }
}
