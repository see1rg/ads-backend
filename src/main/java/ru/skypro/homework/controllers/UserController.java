package ru.skypro.homework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dtos.NewPasswordDto;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.services.AuthService;
import ru.skypro.homework.services.ImageService;
import ru.skypro.homework.services.UserService;

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

    private PasswordEncoder passwordEncoder;

    @Operation(
            operationId = "setPassword",
            summary = "Обновление пароля",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = NewPasswordDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPassword,
                                                      Authentication authentication) {
        log.info("Set password: " + newPassword);
        Optional<UserDto> user = userService.getUser(authentication.getName());
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (authService.changePassword(newPassword, authentication.getName())) {
            return ResponseEntity.ok(new NewPasswordDto());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(
            operationId = "getUser",
            summary = "Получить информацию об авторизованном пользователе",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = UserDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping("/me")
    public ResponseEntity<Optional<UserDto>> getUser(Authentication authentication) {
        log.info("User {}", authentication.getName());
        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @Operation(
            operationId = "updateUser",
            summary = "Обновить информацию об авторизованном пользователе",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = UserDto.class))
                    }),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterReq> updateUser(@RequestBody RegisterReq user, Authentication authentication) {
        log.info("User {} update", authentication.getName());
        return ResponseEntity.ok(userService.update(user, authentication.getName()));
    }

    @Operation(
            operationId = "updateUserImage",
            summary = "Обновить аватар авторизованного пользователя",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam("image") MultipartFile image,
                                                Authentication authentication) throws IOException {
        log.info("User {} update avatar", authentication.getName());
        imageService.saveAvatar(authentication.getName(), image);
        return ResponseEntity.status(200).build();
    }

    @GetMapping(value = "/{id}/getImage", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) {
        return ResponseEntity.ok(imageService.getAvatar(id));
    }
}
