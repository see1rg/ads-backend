package ru.skypro.homework.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.models.User;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.services.UserService;

import java.security.Principal;
import java.util.Optional;

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
    public RegisterReq update(RegisterReq reqUser, Principal principal) {
        log.info("Update user: " + principal);
        User optionalUser = userRepository.findUserByUsername(principal.getName());
        if (optionalUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        User updateUser = userMapper.updateUserFromRegisterReq(reqUser);
        updateUser.setId(optionalUser.getId());
        updateUser.setEmail(optionalUser.getEmail());
        userRepository.save(updateUser);
        return reqUser;
    }

    @Override
    public RegisterReq save(RegisterReq user) {
        log.info("Save user: " + user);
        User newUser = userMapper.updateUserFromRegisterReq(user);
        userRepository.save(newUser);
        return user;
    }
}
