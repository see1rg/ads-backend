package ru.skypro.homework.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dtos.NewPasswordDto;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.services.AuthService;
import ru.skypro.homework.services.ImageService;
import ru.skypro.homework.services.UserService;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final ImageService imageService;

    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPassword,
                                                      @NotNull Authentication authentication) {
        log.info("Set password: " + newPassword);
        Optional<UserDto> user = userService.getUser(authentication.getName());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (authService.changePassword(newPassword, authentication.getName())) {
            return ResponseEntity.ok(new NewPasswordDto());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<Optional<UserDto>> getUser(Authentication authentication) {
        log.info("User {} authenticated", authentication.getName());
        Optional<UserDto> user = userService.getUser(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @PatchMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterReq> updateUser(@RequestBody RegisterReq user, @NotNull Authentication authentication) {
        RegisterReq updatedUser = userService.update(user, authentication);
        log.info("User {} update", authentication.getName());
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam("image") MultipartFile image,@NotNull Authentication authentication) throws IOException {
        log.info("User {} update avatar", authentication.getName());
        imageService.saveAvatar(authentication.getName(), image);
        return ResponseEntity.status(200).build();
    }

    @GetMapping(value = "/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) throws IOException {
        log.info("Get avatar from user with id " + id);
        return ResponseEntity.ok(imageService.getAvatar(id));
    }
}
