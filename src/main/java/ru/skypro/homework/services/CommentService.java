package ru.skypro.homework.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.CommentDto;

import java.io.IOException;
import java.util.Collection;
import java.util.List;


public interface CommentService {

    Collection<CommentDto> getComments(Integer id);

    CommentDto addComment(Integer id, CommentDto commentDto, Authentication authentication) throws IOException;

    boolean deleteComment(Integer adId, Integer id);

    CommentDto updateComment(Integer adId, CommentDto commentDto, Integer id, Authentication authentication) throws IOException;

}
