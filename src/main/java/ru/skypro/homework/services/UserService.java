package ru.skypro.homework.services;


import ru.skypro.homework.dtos.UserDto;

import java.security.Principal;

public interface UserService {
    UserDto updateUser(UserDto userDto, Principal principal);

    UserDto getUser(String email);
}
