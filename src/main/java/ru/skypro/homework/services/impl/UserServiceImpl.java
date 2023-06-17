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

import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto update(UserDto userDto, String email) {
        User updatedUser = userMapper.userDtoToUser(userDto);
        log.info("Update user: " + updatedUser);
        return userMapper.userToUserDto(userRepository.save(updatedUser));
    }

    @Override
    public Optional<UserDto> getUser(String email) {
        log.info("Get user: " + email);
        return userRepository.findUserByEmailIs(email).map(userMapper::userToUserDto);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        log.info("Update user: " + userDto);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User existingUser = optionalUser.get();
        existingUser.setEmail(userDto.getEmail());
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setPhone(userDto.getPhone());

        return userMapper.userToUserDto(userRepository.save(existingUser));
    }

    @Override
    public RegisterReq update(RegisterReq user, String email) {
        log.info("Update user: " + email);
        User optionalUser = userRepository.findByEmail(email);
        if (optionalUser.equals(null)) {
            throw new IllegalArgumentException("User not found");
        }
        optionalUser.setEmail(user.getUsername());
        optionalUser.setFirstName(user.getFirstName());
        optionalUser.setLastName(user.getLastName());
        optionalUser.setPhone(user.getPhone());
        optionalUser.setRole(user.getRole());
        userMapper.userToUserDto(userRepository.save(optionalUser));
        return user;
    }
}
