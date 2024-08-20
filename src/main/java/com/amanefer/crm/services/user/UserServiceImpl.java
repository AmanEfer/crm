package com.amanefer.crm.services.user;

import com.amanefer.crm.dto.user.RegisterUserDto;
import com.amanefer.crm.entities.Role;
import com.amanefer.crm.entities.User;
import com.amanefer.crm.exceptions.UserException;
import com.amanefer.crm.mappers.UserMapper;
import com.amanefer.crm.repositories.UserRepository;
import com.amanefer.crm.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String ID_NOT_FOUND_MESSAGE = "User with ID %d wasn't found";
    public static final String EMAIL_NOT_FOUND_MESSAGE = "User with email '%s' wasn't found";
    private static final String DELETE_MESSAGE = "User with ID %s was successfully deleted";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return findUserInDB(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserException(String.format(EMAIL_NOT_FOUND_MESSAGE, email)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .authorities(getAuthorities(user))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(String.format(EMAIL_NOT_FOUND_MESSAGE, username)));
    }

    @Override
    @Transactional
    public User createUser(RegisterUserDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("Such user already exists");
        }

        User savedUser = userMapper.fromDtoToEntity(user);

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getDefaultRole());

        savedUser.setRoles(roles);
        savedUser.setAuthoredTasks(new HashSet<>());
        savedUser.setAssignedTasks(new HashSet<>());

        return userRepository.save(savedUser);
    }

    @Override
    @Transactional
    public User updateUser(Integer id, RegisterUserDto user) {
        User foundUser = findUserInDB(id);

        User updatableUser = userMapper.fromDtoToEntity(user);
        updatableUser.setId(id);
        updatableUser.setAssignedTasks(foundUser.getAssignedTasks());
        updatableUser.setAuthoredTasks(foundUser.getAuthoredTasks());

        return userRepository.save(updatableUser);
    }

    @Override
    @Transactional
    public String deleteUser(Integer id) {
        findUserInDB(id);
        userRepository.deleteById(id);

        return String.format(DELETE_MESSAGE, id);
    }

    private User findUserInDB(Integer id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserException(String.format(ID_NOT_FOUND_MESSAGE, id)));
    }

    private static Set<SimpleGrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

}
