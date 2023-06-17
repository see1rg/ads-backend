package ru.skypro.homework.services;

import ru.skypro.homework.dtos.NewPasswordDto;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.RoleDto;

import java.security.Principal;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, RoleDto role);
    boolean setPassword(NewPasswordDto newPassword, Principal principal);
}
