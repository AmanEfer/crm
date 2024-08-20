package com.amanefer.crm.mappers;

import com.amanefer.crm.dto.user.UserBasicFieldsDto;
import com.amanefer.crm.dto.user.RegisterUserDto;
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
public interface UserMapper extends BaseMapper<RegisterUserDto, UserResponseDto, User> {

    UserBasicFieldsDto fromUserToBasicFieldsDto(User user);

    List<UserBasicFieldsDto> fromEntityListToDtoList(List<User> users);
}
