package com.amanefer.crm.services.user;

import com.amanefer.crm.dto.user.UserRequestDto;
import com.amanefer.crm.dto.user.UserResponseDto;
import com.amanefer.crm.entities.User;
import com.amanefer.crm.exceptions.UserException;
import com.amanefer.crm.mappers.UserMapper;
import com.amanefer.crm.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String ID_NOT_FOUND_MESSAGE = "User with ID %d wasn't found";
    public static final String EMAIL_NOT_FOUND_MESSAGE = "User with email '%s' wasn't found";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.fromEntityListToDtoList(userRepository.findAll());
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userMapper.fromEntityToDto(userRepository.findById(id)
                .orElseThrow(() -> new UserException(String.format(ID_NOT_FOUND_MESSAGE, id))));
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        return userMapper.fromEntityToDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(String.format(EMAIL_NOT_FOUND_MESSAGE, email))));
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto user) {
        User saved = userRepository.save(userMapper.fromDtoToEntity(user));

        return userMapper.fromEntityToDto(saved);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto user) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException(String.format(ID_NOT_FOUND_MESSAGE, id)));

        User updatableUser = userMapper.fromDtoToEntity(user);
        updatableUser.setId(id);
        updatableUser.setAssignedTasks(foundUser.getAssignedTasks());
        updatableUser.setAuthoredTasks(foundUser.getAuthoredTasks());

        return userMapper.fromEntityToDto(userRepository.save(updatableUser));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
