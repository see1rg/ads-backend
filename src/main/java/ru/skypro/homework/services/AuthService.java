package ru.skypro.homework.services;

import ru.skypro.homework.dtos.NewPasswordDto;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
    boolean changePassword(NewPasswordDto newPasswordDto, String userName);
}
