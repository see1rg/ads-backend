package ru.skypro.homework.services;


import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.UserDto;

import java.util.Optional;

@Service
public interface UserService {

    UserDto update(UserDto user, String email);

    Optional<UserDto> getUser(String name);

    UserDto updateUser(UserDto user, Integer id);

}
