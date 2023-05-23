package ru.skypro.homework.controllers;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.User;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/set_password")
    public ResponseEntity<String> setPassword( @PathVariable String newPassword, Authentication authentication) {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser(Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/me")
    public ResponseEntity<User> updateUser(@RequestBody String newPassword, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam MultipartFile image, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
