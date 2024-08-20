package com.amanefer.crm.services.task;

import com.amanefer.crm.entities.Task;
import com.amanefer.crm.entities.User;

import java.util.List;

public interface TaskService {

    Task getTaskById(Integer id);

    List<Task> getTasksByAuthor(User author);

    List<Task> getTasksByAssignee(User assignee);

    Task createTask(Task task);

    Task updateTask(Task task);

    void deleteTask(Integer id);
}
