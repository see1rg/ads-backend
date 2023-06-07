package ru.skypro.homework.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dtos.CommentDto;
import ru.skypro.homework.mappers.CommentMapper;
import ru.skypro.homework.models.Comment;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.services.CommentService;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public Collection<CommentDto> getComments() {
        Collection<Comment> comments = commentRepository.findAll();
        log.info("Get all comments: " + comments);
        return CommentMapper.INSTANCE.commentCollectionToCommentDto(comments);
    }

    @Override
    public CommentDto addComment(CommentDto commentDto) {
        Comment newComment = CommentMapper.INSTANCE.INSTANCE.commentDtoToComment(commentDto);
        log.info("Save comment: " + newComment);
        return CommentMapper.INSTANCE.INSTANCE.commentToCommentDto(newComment);
    }

    @Override
    public boolean deleteComment(Long id) {
        log.info("Delete comment: " + id);
        commentRepository.deleteById(id);
        return false;
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Long id) {
        Comment comment = CommentMapper.INSTANCE.INSTANCE.commentDtoToComment(commentDto);
        log.info("Update comment: " + comment);
        return CommentMapper.INSTANCE.INSTANCE.commentToCommentDto(commentRepository.save(comment));
    }
}
