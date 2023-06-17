package ru.skypro.homework.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.services.impl.ImageServiceImpl;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageServiceImpl imageServiceImpl;

    public ImageController(ImageServiceImpl imageServiceImpl) {
        this.imageServiceImpl = imageServiceImpl;
    }

    /**
     * Получение аватара пользовател
    **/
    @GetMapping(value = "/user/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAdsAvatar(@PathVariable("id") String id) {

    return ResponseEntity.ok().body(imageServiceImpl.downloadImage(id));
    }
    /**
     * Получение картинки объявления
     **/
    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAdsAvatar2(@PathVariable("id") String id) {

    return ResponseEntity.ok().body(imageServiceImpl.downloadImage(id));
    }

}
