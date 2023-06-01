package ru.skypro.homework.services;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.CommentDto;

import java.util.List;

@Service
public interface CommentService {

    public List<CommentDto> getComments();

    public CommentDto postComment();

    public void deleteComment();

    public CommentDto patchComment();

}
