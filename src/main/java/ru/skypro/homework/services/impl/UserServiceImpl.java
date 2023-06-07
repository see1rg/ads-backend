package ru.skypro.homework.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.models.User;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.services.UserService;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto update(UserDto userDto, String email) {
        User updatedUser = UserMapper.INSTANCE.userDtoToUser(userDto);
        log.info("Update user: " + updatedUser);
        return UserMapper.INSTANCE.userToUserDto(userRepository.save(updatedUser));
    }

    @Override
    public Optional<UserDto> getUser(String email) {
        log.info("Get user: " + email);
        return userRepository.findUserByEmailIs(email).map(UserMapper.INSTANCE::userToUserDto);
    }

    @Override
    public UserDto updateUser(UserDto user, Long id) {
        log.info("Update user: " + user);
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        return UserMapper.INSTANCE.userToUserDto(
                userRepository.save(UserMapper.INSTANCE.userDtoToUser(user))
        );
    }
}
