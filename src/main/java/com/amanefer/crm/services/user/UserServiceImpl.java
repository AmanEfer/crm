package com.amanefer.crm.services.user;

import com.amanefer.crm.entities.Role;
import com.amanefer.crm.entities.User;
import com.amanefer.crm.exceptions.UserException;
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
import java.util.function.Function;
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
                .map(convertToUserDetails())
                .orElseThrow(() -> new UsernameNotFoundException(String.format(EMAIL_NOT_FOUND_MESSAGE, username)));
    }

    @Override
    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("Such user already exists");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getDefaultRole());

        user.setRoles(roles);
        user.setAuthoredTasks(new HashSet<>());
        user.setAssignedTasks(new HashSet<>());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Integer id, User user) {
        User foundUser = findUserInDB(id);

        user.setId(id);
        user.setAssignedTasks(foundUser.getAssignedTasks());
        user.setAuthoredTasks(foundUser.getAuthoredTasks());

        return userRepository.save(user);
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

    private static Function<User, UserDetails> convertToUserDetails() {
        return user -> org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(getAuthorities(user))
                .build();
    }

}
