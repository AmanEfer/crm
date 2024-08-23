package com.amanefer.crm.services.comment;

import com.amanefer.crm.dto.comment.CreateCommentDto;
import com.amanefer.crm.dto.task.TaskResponseDto;
import com.amanefer.crm.entities.Comment;
import com.amanefer.crm.entities.Task;
import com.amanefer.crm.entities.User;
import com.amanefer.crm.mappers.CommentMapper;
import com.amanefer.crm.mappers.TaskMapper;
import com.amanefer.crm.repositories.CommentRepository;
import com.amanefer.crm.services.task.TaskService;
import com.amanefer.crm.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponseDto addNewComment(String email, Integer id, CreateCommentDto dto) {
        User user = userService.getUserByEmailAsEntity(email);
        Task task = taskService.getTaskByIdAsEntity(id);

        Comment comment = commentMapper.fromDtoToEntity(dto);
        comment.setTask(task);
        comment.setAuthorId(user.getId());

        Comment savedComment = commentRepository.save(comment);

        task.getComments().add(savedComment);

        return taskMapper.fromEntityToDto(task);
    }

}
