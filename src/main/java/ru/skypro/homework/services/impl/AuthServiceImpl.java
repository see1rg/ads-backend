package ru.skypro.homework.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.NewPasswordDto;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.Role;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.services.AuthService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    @Override
    public boolean login(String userName, String password) {
        if (userRepository.findUserByUsername(userName) == null) {
            log.info("Пользователь с именем {} не найден", userName);
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword()); //todo
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (userRepository.findUserByUsername(registerReq.getUsername()) != null) {
            log.info("Пользователь с именем {} уже существует", registerReq.getUsername());
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(role.name())
                        .build());
        return true;
    }

    @Override
    public boolean changePassword(NewPasswordDto newPasswordDto, String userName) { //todo
        if (manager.userExists(userName)) {
            String encodedNewPassword = encoder.encode(newPasswordDto.getNewPassword());
            manager.changePassword(userName, encodedNewPassword);
            return true;
        }
        log.info("Пользователь с именем {} не найден", userName);
        return false;
    }
}
