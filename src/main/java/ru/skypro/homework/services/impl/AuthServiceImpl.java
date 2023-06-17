package ru.skypro.homework.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.NewPasswordDto;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.RoleDto;
import ru.skypro.homework.models.User;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.services.AuthService;

import java.security.Principal;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserDetailsServiceImpl manager;
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  public AuthServiceImpl(UserDetailsServiceImpl manager, UserRepository userRepository) {
    this.manager = manager;
    this.userRepository = userRepository;
    this.encoder = new BCryptPasswordEncoder();
  }

  /**
   * Авторизация по логину и паролю
   */
  @Override
  public boolean login(String userName, String password) {
    UserDetails userDetails = manager.loadUserByUsername(userName);
    String encryptedPassword = userDetails.getPassword();
    return encoder.matches(password, encryptedPassword);
  }

  /**
   * Создание нового пользователя
   */
  @Override
  public boolean register(RegisterReq regReq, RoleDto role) {
    User userFromDB = userRepository.findByUsername(regReq.getUsername());
    if (userFromDB != null) {
      return false;
    }
    User user = new User();
    user.setEmail(regReq.getUsername());
    user.setPassword(encoder.encode(regReq.getPassword()));
    user.setFirstName(regReq.getFirstName());
    user.setLastName(regReq.getLastName());
    user.setPhone(regReq.getPhone());
    user.setRole("ROLE_"+role.name());
    userRepository.save(user);
    return true;
  }
  /**
   * Изменение пароля пользователя
   */
  @Override
  public boolean setPassword(NewPasswordDto newPassword, Principal principal) {
    UserDetails userDetails = manager.loadUserByUsername(principal.getName());
    String encryptedPassword = userDetails.getPassword();
    if (encoder.matches(newPassword.getCurrentPassword(), encryptedPassword)) {
      User userFromDB = userRepository.findByUsername(principal.getName());
      userFromDB.setPassword(encoder.encode(newPassword.getNewPassword()));
      userRepository.save(userFromDB);
      return true;
    }
    return false;
  }


}
