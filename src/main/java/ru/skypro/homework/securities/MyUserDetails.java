package ru.skypro.homework.securities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dtos.SecurityUserDto;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class MyUserDetails implements UserDetails {

    private SecurityUserDto user;

    public void setUserDto(SecurityUserDto userDto) {
        this.user = userDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(user)
                .map(SecurityUserDto::getRole)
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .map(Collections::singleton)
                .orElse(Collections.emptySet());
    }

    @Override
    public String getPassword() {
        return Optional.ofNullable(user)
                .map(SecurityUserDto::getPassword)
                .orElse(null);
    }

    @Override
    public String getUsername() {
        return Optional.ofNullable(user)
                .map(SecurityUserDto::getEmail)
                .orElse(null);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
