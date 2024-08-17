package com.amanefer.crm.mappers;

import com.amanefer.crm.dto.user.UserRequestDto;
import com.amanefer.crm.dto.user.UserResponseDto;
import com.amanefer.crm.entities.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = TaskMapper.class)
public interface UserMapper extends BaseMapper<UserRequestDto, UserResponseDto, User> {
    List<UserResponseDto> fromEntityListToDtoList(List<User> users);
}
