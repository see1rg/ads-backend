package ru.skypro.homework.services;

import org.springframework.stereotype.Service;
import ru.skypro.homework.repositories.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }




}
