package com.amanefer.crm.mappers;

import com.amanefer.crm.dto.task.TaskRequestDto;
import com.amanefer.crm.dto.task.TaskResponseDto;
import com.amanefer.crm.entities.Task;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = CommentMapper.class)
public interface TaskMapper extends BaseMapper<TaskRequestDto, TaskResponseDto, Task> {

}
