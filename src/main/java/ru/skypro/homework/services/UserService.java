package ru.skypro.homework.services;


import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.UserDto;

import java.security.Principal;
import java.util.Optional;

@Service
public interface UserService {

    Optional<UserDto> getUser(String name);


    RegisterReq update(RegisterReq user, Principal principal);

}
