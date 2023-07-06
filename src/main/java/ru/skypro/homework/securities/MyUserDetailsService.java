package ru.skypro.homework.securities;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.SecurityUserDto;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MyUserDetails userDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUserDto securityUserDto = userRepository.findByEmailIgnoreCase(username)
                .map(userMapper::toSecurityDto)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
        userDetails.setUserDto(securityUserDto);
        return userDetails;
    }
}
