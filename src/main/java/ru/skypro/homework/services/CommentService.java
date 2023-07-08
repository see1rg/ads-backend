package ru.skypro.homework.services;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dtos.CommentDto;
import ru.skypro.homework.dtos.ResponseWrapperComment;
import ru.skypro.homework.models.Comment;

import java.io.IOException;


public interface CommentService {
    Comment findCommentById(Integer id);

    ResponseWrapperComment getComments(Integer id);

    CommentDto addComment(Integer id, CommentDto commentDto, Authentication authentication) throws IOException;

    boolean deleteComment(Integer adId, Integer id);

    CommentDto updateComment(Integer adId, CommentDto commentDto, Integer commentId, Authentication authentication) throws IOException;
}
