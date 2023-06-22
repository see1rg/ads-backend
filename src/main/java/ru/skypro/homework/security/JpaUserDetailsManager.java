package ru.skypro.homework.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.models.User;
import ru.skypro.homework.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
@Slf4j
@Service
public class JpaUserDetailsManager implements UserDetailsManager {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername: " + username);
        return repository.findUserByUsername(username);
    }

    @Override
    public void createUser(UserDetails user) {
        log.info("createUser: " + user);
        User userForSave = new User();
        userForSave.setPassword(user.getPassword());
        userForSave.setEnabled(true);
        userForSave.setAuthorities(Collections.singleton(new AuthGrantedAuthority()));
        userForSave.setEmail(user.getUsername());
        userForSave.setAccountExpired(user.isAccountNonExpired());
        userForSave.setAccountLocked(user.isAccountNonLocked());
        userForSave.setCredentialsExpired(user.isCredentialsNonExpired());
        repository.save((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        log.info("updateUser: " + user);
        repository.save((User) user);

    }

    @Override
    public void deleteUser(String username) {
        log.info("deleteUser: " + username);
        User userDetails = repository.findUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("No User found for username -> " + username);
        }
        repository.delete(userDetails);
    }

    /**
     * This method assumes that both oldPassword and the newPassword params
     * are encoded with configured passwordEncoder
     *
     * @param oldPassword the old password of the user
     * @param newPassword the new password of the user
     */
    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword) {
        log.info("changePassword: " + oldPassword + " " + newPassword);
        Optional<Object> userDetails = repository.findByPassword(oldPassword);
        if (userDetails.isEmpty()) {
            throw new UsernameNotFoundException("Invalid password ");
        }
        User userDetails1 = (User) userDetails.get();
        userDetails1.setPassword(newPassword);
        repository.save(userDetails1);
    }

    @Override
    public boolean userExists(String username) {
        log.info("userExists: " + username);
        User userDetails = repository.findUserByUsername(username);
        return userDetails != null;
    }
}

