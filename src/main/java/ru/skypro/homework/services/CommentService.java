package ru.skypro.homework.services;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.CommentDto;

import java.io.IOException;
import java.util.List;


public interface CommentService {

    public Iterable<CommentDto> getComments();

    public CommentDto addComment(CommentDto commentDto) throws IOException;

    public boolean deleteComment(Long id);

    public CommentDto updateComment(CommentDto commentDto, Long id);

}
