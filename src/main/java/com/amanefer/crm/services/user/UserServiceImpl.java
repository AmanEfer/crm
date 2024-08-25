package com.amanefer.crm.services.user;

import com.amanefer.crm.dto.common.ResponseDto;
import com.amanefer.crm.dto.user.RegisterUserDto;
import com.amanefer.crm.dto.user.UpdateUserDto;
import com.amanefer.crm.dto.user.UserResponseDto;
import com.amanefer.crm.entities.Role;
import com.amanefer.crm.entities.User;
import com.amanefer.crm.exceptions.UserConflictException;
import com.amanefer.crm.mappers.UserMapper;
import com.amanefer.crm.repositories.UserRepository;
import com.amanefer.crm.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public static final String DELETE_MESSAGE = "User with ID %s was successfully deleted";
    public static final String ALREADY_EXISTS_MESSAGE = "Such user already exists";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getAllUsers() {

        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserResponseDto getUserById(Integer id) {

        return userMapper.toDto(findUserInDB(id));
    }

    @Override
    public User getUserByIdAsEntity(Integer id) {

        return userRepository.findUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {

        return userMapper.toDto(getUserByEmailAsEntity(email));
    }

    @Override
    public User getUserByEmailAsEntity(String email) {

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(EMAIL_NOT_FOUND_MESSAGE, email)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findUserByEmail(username)
                .map(convertToUserDetails())
                .orElseThrow(() -> new UsernameNotFoundException(String.format(EMAIL_NOT_FOUND_MESSAGE, username)));
    }

    @Override
    @Transactional
    public UserResponseDto createUser(RegisterUserDto dto) {
        User user = userMapper.toEntity(dto);

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserConflictException(ALREADY_EXISTS_MESSAGE);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getDefaultRole());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        user.setAuthoredTasks(new HashSet<>());
        user.setAssignedTasks(new HashSet<>());

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Integer id, UpdateUserDto dto) {
        User updatableUser = findUserInDB(id);

        updatableUser.setName(dto.getName());
        updatableUser.setEmail(dto.getEmail());

        return userMapper.toDto(updatableUser);
    }

    @Override
    @Transactional
    public ResponseDto deleteUser(Integer id) {
        findUserInDB(id);
        userRepository.deleteById(id);

        return new ResponseDto(String.format(DELETE_MESSAGE, id));
    }

    private User findUserInDB(Integer id) {

        return userRepository.findUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, id)));
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
