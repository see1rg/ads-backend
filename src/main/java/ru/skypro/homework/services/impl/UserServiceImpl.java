package ru.skypro.homework.services.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.models.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.services.UserService;

import java.security.Principal;

@Service
public class UserServiceImpl  implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Обновление пользователя
     */
    @Override
    public UserDto updateUser(UserDto userDto, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        if (user == null) {
            throw new UserNotFoundException();
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setImage(userDto.getImage());
        userRepository.save(user);
    return mapper.userToUserDto(user);

    }
    /**
     * Получение пользователя
     */
    @Override
    public UserDto getUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }
    return mapper.userToUserDto(user);
    }

}
