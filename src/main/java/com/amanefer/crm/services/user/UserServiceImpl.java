package com.amanefer.crm.services.user;

import com.amanefer.crm.entities.User;
import com.amanefer.crm.exceptions.UserException;
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

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(String.format(ID_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(String.format(EMAIL_NOT_FOUND_MESSAGE, email)));
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) {
        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException(String.format(ID_NOT_FOUND_MESSAGE, id)));

        user.setId(id);
        user.setAssignedTasks(updatedUser.getAssignedTasks());
        user.setAuthoredTasks(updatedUser.getAuthoredTasks());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
