package ru.skypro.homework.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dtos.CommentDto;
import ru.skypro.homework.dtos.ResponseWrapperComment;
import ru.skypro.homework.services.CommentService;

import java.io.IOException;


@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("{id}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getComments(id));
    }

    @PostMapping("{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer id,
                                                 @RequestBody CommentDto commentDto) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(id, commentDto, authentication));
    }

    @PreAuthorize("hasAuthority('ADMIN') or  @commentServiceImpl.findCommentById(#commentId).authorId.email == #authentication.name")
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        boolean result = commentService.deleteComment(adId, commentId);
        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or @commentServiceImpl.findCommentById(#commentId).authorId.email" +
            " == #authentication.name")
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer adId,
                                                    @PathVariable Integer commentId,
                                                    Authentication authentication) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(adId, commentDto,
                commentId, authentication));
    }
}
