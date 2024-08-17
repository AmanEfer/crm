package com.amanefer.crm.services.user;

import com.amanefer.crm.dto.user.UserBasicFieldsDto;
import com.amanefer.crm.dto.user.UserRequestDto;
import com.amanefer.crm.dto.user.UserResponseDto;
import com.amanefer.crm.entities.User;
import com.amanefer.crm.exceptions.UserNotFoundException;
import com.amanefer.crm.mappers.UserMapper;
import com.amanefer.crm.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String ID_NOT_FOUND_MESSAGE = "User with ID %d wasn't found";
    public static final String EMAIL_NOT_FOUND_MESSAGE = "User with email '%s' wasn't found";
    private static final String DELETE_MESSAGE = "User with ID %s was successfully deleted";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserBasicFieldsDto> getAllUsers() {
        return userMapper.fromEntityListToDtoList(userRepository.findAll());
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userMapper.fromEntityToDto(findUserInDB(id));
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        return userMapper.fromEntityToDto(userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format(EMAIL_NOT_FOUND_MESSAGE, email))));
    }

    @Override
    @Transactional
    public UserBasicFieldsDto createUser(UserRequestDto user) {
        User savedUser = userMapper.fromDtoToEntity(user);
        savedUser.setAuthoredTasks(new HashSet<>());
        savedUser.setAssignedTasks(new HashSet<>());

        savedUser = userRepository.save(savedUser);

        return userMapper.fromUserToBasicFieldsDto(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto user) {
        User foundUser = findUserInDB(id);

        User updatableUser = userMapper.fromDtoToEntity(user);
        updatableUser.setId(id);
        updatableUser.setAssignedTasks(foundUser.getAssignedTasks());
        updatableUser.setAuthoredTasks(foundUser.getAuthoredTasks());

        return userMapper.fromEntityToDto(userRepository.save(updatableUser));
    }

    @Override
    @Transactional
    public String deleteUser(Long id) {
        findUserInDB(id);
        userRepository.deleteById(id);

        return String.format(DELETE_MESSAGE, id);
    }


    private User findUserInDB(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, id)));
    }
}
