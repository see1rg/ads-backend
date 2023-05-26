package ru.skypro.homework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;

import java.util.List;


@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {

    @Operation(
            operationId = "getComments",
            summary =  "Получить комментарии объявления",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ОК",
                            content = {@Content(
                                    mediaType =  "*/*",
                                   schema = @Schema(implementation = CommentDto.class))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )

    @GetMapping("{id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            operationId = "postComment",
            summary = "Добавить комментарий к объявлению",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(
                                    mediaType =  "*/*",
                                    schema = @Schema(implementation = CommentDto.class))
                            }),
                     @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping("{id}/comments")
    public ResponseEntity<CommentDto> postComment(@PathVariable Long id,@Parameter(description = "Необходимо корректно" +
            " заполнить комментарий", example = "Тест"
    ) @RequestBody CommentDto commentDto) {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(
            operationId = "deleteComment",
            summary = "Удалить комментарий",
            tags = "Комментарии",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаляем комментарий ",
                            content = {@Content(
                                    mediaType =  "*/*",
                                    schema = @Schema(implementation = CommentDto.class))
                            }
                    ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
            }

    )
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long adId, @PathVariable Long commentId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            operationId = "patchComment",
            summary = "Обновить комментарий",
            tags = "Комментарии",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изменяемый комментарий ",
                            content = {@Content(
                                    mediaType =  "*/*",
                                    schema = @Schema(implementation = CommentDto.class))
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> patchComment(@RequestBody CommentDto commentDto, @PathVariable Long adId, @PathVariable Long commentId) {
        return ResponseEntity.ok().build();
    }



}
