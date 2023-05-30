package ru.skypro.homework.services;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserDto;

@Service
public interface UserService {

    public UserDto update(UserDto user, String email);

    public UserDto getUser(String email);

    public UserDto updateUser();

    public void updateUserImage();


}
