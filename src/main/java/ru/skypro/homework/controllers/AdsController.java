package ru.skypro.homework.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.dtos.AdsDtoFull;
import ru.skypro.homework.dtos.ResponseWrapper;
import ru.skypro.homework.services.AdsService;
import ru.skypro.homework.services.ImageService;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    private final AdsService adsService;
    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<AdsDto>> getAllAds(@RequestParam(required = false) String title) {
        ResponseWrapper<AdsDto> ads = new ResponseWrapper<>(adsService.getAllAds(title));
        return ResponseEntity.ok(ads);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAd(@NotNull Authentication authentication,
                                        @RequestPart("image") MultipartFile image,
                                        @RequestPart("properties") AdsDto properties) throws IOException {
        log.info("Add ad: " + properties);
        return ResponseEntity.ok(adsService.addAd(properties, image, authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdsDtoFull> getAds(@PathVariable Integer id) {
        log.info("Get ads: " + id);
        return ResponseEntity.ok(adsService.getAds(id));
    }

    @PreAuthorize("hasAuthority('ADMIN') or @adsServiceImpl.getAds(#id).email == authentication.principal.username")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable Integer id) {
        boolean result = adsService.removeAd(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or @adsServiceImpl.getAds(#id).email == authentication.principal.username")
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@RequestBody AdsDto ads, @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(adsService.updateAds(ads, id));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<AdsDto>> getMe(@NotNull Authentication authentication) {
        log.info("Get me: " + authentication.getName());
        ResponseWrapper<AdsDto> ads = new ResponseWrapper<>(adsService.getMe(authentication.getName()));
        return ResponseEntity.ok(ads);
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id,
                                              @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(adsService.updateImage(id, image));
    }

    @GetMapping(value = "/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) throws IOException {
        log.info("Get image from ads with id " + id);
        return ResponseEntity.ok(imageService.getImage(id));
    }
}
