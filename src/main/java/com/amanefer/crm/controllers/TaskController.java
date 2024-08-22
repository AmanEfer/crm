package com.amanefer.crm.controllers;

import com.amanefer.crm.dto.task.TaskResponseAsPage;
import com.amanefer.crm.dto.task.TaskRequestDto;
import com.amanefer.crm.dto.task.TaskResponseDto;
import com.amanefer.crm.entities.Task;
import com.amanefer.crm.entities.User;
import com.amanefer.crm.services.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<TaskResponseAsPage> getAllTasks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "3") int size,
            @RequestParam(value = "only_my", required = false, defaultValue = "true") boolean onlyMy,
            @AuthenticationPrincipal UserDetails user) {

        TaskResponseAsPage response;

        if (onlyMy)
            response = taskService.getCurrentUserAllTasks(PageRequest.of(page, size), user.getUsername());
        else
            response = taskService.getAllUsersAllTasks(PageRequest.of(page, size));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/author/{author}")
    public List<Task> getTasksByAuthor(@PathVariable User author) {
        return taskService.getTasksByAuthor(author);
    }

    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<List<Task>> getTasksByAssignee(@PathVariable int assigneeId) {
        User assignee = new User();
        assignee.setId(assigneeId);
        List<Task> tasks = taskService.getTasksByAssignee(assignee);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto dto, Principal principal) {
        return new ResponseEntity<>(taskService.createTask(dto, principal.getName()), HttpStatus.OK);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Integer id, @RequestBody TaskRequestDto dto) {
//        Task updatedTask = taskService.updateTask(dto);
//        return ResponseEntity.ok(updatedTask);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id, @AuthenticationPrincipal UserDetails user) {
        taskService.deleteTask(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
