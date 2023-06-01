package ru.skypro.homework.services;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dtos.UserDto;

import java.io.IOException;
import java.util.Optional;

@Service
public interface UserService {

    public UserDto update(UserDto user, String email);

    public Optional<UserDto> getUser();

    public UserDto updateUser(UserDto user, Long id);

    public byte[] updateUserImage(Long id, MultipartFile image) throws IOException;


}
