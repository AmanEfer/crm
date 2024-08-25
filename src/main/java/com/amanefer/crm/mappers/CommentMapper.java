package com.amanefer.crm.mappers;

import com.amanefer.crm.dto.comment.CommentResponseDto;
import com.amanefer.crm.dto.comment.CreateCommentDto;
import com.amanefer.crm.entities.Comment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper extends BaseMapper<CreateCommentDto, CommentResponseDto, Comment> {

    @Mapping(source = "task.id", target = "taskId")
    CommentResponseDto toDto(Comment comment);
}
