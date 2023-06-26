package ru.skypro.homework.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.Role;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.models.User;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.services.UserService;

import java.security.Principal;
import java.util.Optional;

import static ru.skypro.homework.dtos.Role.USER;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserDto> getUser(String email) {
        log.info("Get user: " + email);
        User user = userRepository.findUserByUsername(email);
        UserDto userDto = userMapper.userToUserDto(user);
        return Optional.ofNullable(userDto);
    }

    @Override
    public RegisterReq update(RegisterReq user, Principal principal) {
        log.info("Update user: " + principal);
        User optionalUser = userRepository.findUserByUsername(principal.getName());
        if (optionalUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        ModelMapper mapper = new ModelMapper(); //todo заменить на mapstruct
        mapper.map(user, optionalUser);
        userMapper.userToUserDto(userRepository.save(optionalUser));
        return user;
    }

    @Override
    public RegisterReq update(RegisterReq user) {
        Role role = user.getRole() == null ? USER : user.getRole();

        log.info("Update user: " + user);
        User optionalUser = userRepository.findUserByUsername(user.getUsername());
        if (optionalUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        ModelMapper mapper = new ModelMapper();
        mapper.map(user, optionalUser);
        optionalUser.setRole(role);
        return user;
    }

    @Override
    public RegisterReq save(RegisterReq user) {
        log.info("Save user: " + user);
        User newUser = new User(); // todo заменить на mapstruct
        ModelMapper mapper = new ModelMapper();
        mapper.map(user, newUser);
        newUser.setEmail(user.getUsername());
        userRepository.save(newUser);
        return user;
    }
}
