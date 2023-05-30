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
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.models.Ads;

import java.util.List;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

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
    public ResponseEntity<List<AdsDto>> getAllAds() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            operationId = "addAd",
            summary = "Добавить объявление",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = {
                            @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAd(@RequestParam("properties") Ads ads, @RequestParam MultipartFile image) {
        return new ResponseEntity<>(HttpStatus.CREATED);
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
        return new ResponseEntity<>(HttpStatus.OK);
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
    public ResponseEntity<AdsDto> updateAds(@RequestBody Ads ads, @PathVariable Long id) {
        return ResponseEntity.ok().build();
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
                            @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok().build();
    }
}
