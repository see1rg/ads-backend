package ru.skypro.homework.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.NewPasswordDto;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.Role;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repositories.AuthGrantedAuthorityRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.security.AuthGrantedAuthority;
import ru.skypro.homework.security.JpaUserDetailsManager;
import ru.skypro.homework.services.AuthService;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JpaUserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AuthGrantedAuthorityRepository authGrantedAuthorityRepository;


    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            log.info("Пользователь с именем {} не найден", userName);
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (manager.userExists(registerReq.getUsername())) {
            log.info("Пользователь с именем {} уже существует", registerReq.getUsername());
            return false;
        }

        ru.skypro.homework.models.User saveUser = new ru.skypro.homework.models.User();
        saveUser.setEmail("user@gmail.com");
        saveUser.setPassword(encoder.encode("password"));
        saveUser.setEnabled(true);
        saveUser.setCredentialsExpired(false);
        saveUser.setAccountExpired(false);
        saveUser.setAccountLocked(false);

//        AuthGrantedAuthority grantedAuthority = new AuthGrantedAuthority();
//        grantedAuthority.setAuthority("USER");
//        grantedAuthority.setUser(saveUser);
//        saveUser.setAuthorities(Collections.singleton(grantedAuthority));
//        authGrantedAuthorityRepository.save(grantedAuthority);

        ru.skypro.homework.models.User userDetails = new ru.skypro.homework.models.User();
        userDetails.setEmail(registerReq.getUsername());
        userDetails.setPassword(encoder.encode(registerReq.getPassword()));
        userDetails.setEnabled(true);
        userDetails.setAccountExpired(false);
        userDetails.setAccountLocked(false);
        userDetails.setCredentialsExpired(false);
        userDetails.setRole(role);
        userDetails.setPhone(registerReq.getPhone());
        userDetails.setFirstName(registerReq.getFirstName());
        userDetails.setLastName(registerReq.getLastName());

//        UserDetails userDetails = User.builder()
//                .passwordEncoder(this.encoder::encode)
//                .password(registerReq.getPassword())
//                .username(registerReq.getUsername())
//                .credentialsExpired(true)
//                .accountExpired(false)
//                .accountLocked(false)
//                .credentialsExpired(false)
//                .authorities(grantedAuthority) //todo
//                .roles(role.name())
//                .build();

        manager.createUser(saveUser);
        manager.createUser(userDetails);
        return true;
    }

    @Override
    public boolean changePassword(NewPasswordDto newPasswordDto, String userName) {
        if (manager.userExists(userName)) {
            String encodedNewPassword = encoder.encode(newPasswordDto.getNewPassword());
            manager.changePassword(userName, encodedNewPassword);
            return true;
        }
        log.info("Пользователь с именем {} не найден", userName);
        return false;
    }
}
