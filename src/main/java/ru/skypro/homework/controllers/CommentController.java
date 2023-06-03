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
import ru.skypro.homework.dtos.CommentDto;
import ru.skypro.homework.services.CommentService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {

    private final CommentService commentService;

    @Operation(
            operationId = "getComments",
            summary = "Получить комментарии объявления",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ОК",
                            content = {@Content(
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = CommentDto.class))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )

    @GetMapping("{id}/comments")
    public ResponseEntity<Iterable<CommentDto>> getComments(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getComments());
    }

    @Operation(
            operationId = "addComment",
            summary = "Добавить комментарий к объявлению",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = CommentDto.class))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping("{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long id, @Parameter(description = "Необходимо корректно" +
            " заполнить комментарий", example = "Тест"
    ) @RequestBody CommentDto commentDto) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(commentDto));
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
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = CommentDto.class))
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }

    )
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long adId, @PathVariable Long commentId) {
        boolean result = commentService.deleteComment(commentId);
        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            operationId = "updateComment",
            summary = "Обновить комментарий",
            tags = "Комментарии",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изменяемый комментарий ",
                            content = {@Content(
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = CommentDto.class))
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Long adId, @PathVariable Long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentDto, commentId));
    }
}
