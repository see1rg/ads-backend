package ru.skypro.homework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.services.AdsService;

import java.io.IOException;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    private final AdsService adsService;

    @Operation(
            operationId = "getAllAds",
            summary = "Получить все объявления",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping
    public ResponseEntity<Iterable<AdsDto>> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @Operation(
            operationId = "addAd",
            summary = "Добавить объявление",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = {
                            @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAd(@RequestParam("properties") AdsDto ads,
                                        @RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(adsService.addAd(ads, image));
    }

    @Operation(
            operationId = "getAds",
            summary = "Получить информацию об объявлении",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<AdsDto> getAds(@Parameter(description = "Id объявления") @PathVariable Long id) {
        return ResponseEntity.of(adsService.getAds(id));
    }

    @Operation(
            operationId = "removeAd",
            summary = "Удалить объявление",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@Parameter(description = "Id объявления") @PathVariable Long id) {
        boolean result = adsService.removeAd(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            operationId = "updateAds",
            summary = "Обновить информацию об объявлении",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@RequestBody AdsDto ads, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(adsService.updateAds(ads, id));
    }

    @Operation(
            operationId = "getAdsMe",
            summary = "Получить объявления авторизованного пользователя",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping("/ads/me")
    public ResponseEntity<AdsDto> getMe() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            operationId = "updateImage",
            summary = "Обновить картинку объявления",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Long id,
                                              @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(adsService.updateImage(id, image));
    }
}
