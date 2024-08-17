package com.amanefer.crm.services.task;

import com.amanefer.crm.entities.Task;
import com.amanefer.crm.entities.User;
import com.amanefer.crm.exceptions.TaskException;
import com.amanefer.crm.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskException(String.format("Task with ID %d wasn't found", id)));
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
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
