package ru.skypro.homework.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.CommentDto;

import java.io.IOException;
import java.util.List;


public interface CommentService {

    public Iterable<CommentDto> getComments(Long id);

    public CommentDto addComment(Long id, CommentDto commentDto, Authentication authentication) throws IOException;

    public boolean deleteComment(Long adId, Long id);

    public CommentDto updateComment(Long adId, CommentDto commentDto, Long id, Authentication authentication) throws IOException;

}
