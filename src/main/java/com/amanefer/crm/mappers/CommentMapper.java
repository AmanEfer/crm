package com.amanefer.crm.mappers;

import com.amanefer.crm.dto.comment.CommentRequestDto;
import com.amanefer.crm.dto.comment.CommentResponseDto;
import com.amanefer.crm.entities.Comment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper extends BaseMapper<CommentRequestDto, CommentResponseDto, Comment> {

}
