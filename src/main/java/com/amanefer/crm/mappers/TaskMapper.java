package com.amanefer.crm.mappers;

import com.amanefer.crm.dto.task.TaskRequestDto;
import com.amanefer.crm.dto.task.TaskResponseAsPage;
import com.amanefer.crm.dto.task.TaskResponseDto;
import com.amanefer.crm.entities.Task;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = CommentMapper.class)
public interface TaskMapper extends BaseMapper<TaskRequestDto, TaskResponseDto, Task> {

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "assignee.id", target = "assigneeId")
    TaskResponseDto toDto(Task task);

    List<TaskResponseDto> toDtoList(List<Task> tasks);

    default TaskResponseAsPage fromPageToTaskResponseAsPage(Page<Task> tasksPage) {
        List<TaskResponseDto> content = toDtoList(tasksPage.getContent());
        return TaskResponseAsPage.builder()
                .tasks(content)
                .pageNumber(tasksPage.getNumber())
                .pagesCount(tasksPage.getTotalPages())
                .build();
    }

}
