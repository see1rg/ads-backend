package ru.skypro.homework.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dtos.NewPasswordDto;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.Role;
import ru.skypro.homework.exceptions.UserNotFoundException;
import ru.skypro.homework.models.User;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.securities.MyUserDetailsService;
import ru.skypro.homework.services.AuthService;
import ru.skypro.homework.services.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final MyUserDetailsService manager;

    private final PasswordEncoder encoder;

    private final UserService userService;

    private final UserRepository userRepository;

    @Override
    public boolean login(String userName, String password) {
        if (userRepository.findUserByUsername(userName) == null) {
            log.info("Пользователь с именем {} не найден", userName);
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (userRepository.findUserByUsername(registerReq.getUsername()) != null) {
            log.info("Пользователь с именем {} уже существует", registerReq.getUsername());
            return false;
        }

        log.info("Register user: " + registerReq + " role: " + role);
        RegisterReq newUser = new RegisterReq();
        newUser.setUsername(registerReq.getUsername());
        newUser.setPassword(encoder.encode(registerReq.getPassword()));
        newUser.setFirstName(registerReq.getFirstName());
        newUser.setLastName(registerReq.getLastName());
        newUser.setPhone(registerReq.getPhone());
        newUser.setRole(role);
        userService.save(newUser);

        return true;
    }

    @Override
    public boolean changePassword(NewPasswordDto newPasswordDto, String userName) {
        log.info("Change password for user: " + userName);
           User user = userRepository.findByEmailIgnoreCase(userName).orElseThrow(UserNotFoundException::new);
            user.setPassword(encoder.encode(newPasswordDto.getNewPassword()));
            userRepository.save(user);
            return true;
    }
}
