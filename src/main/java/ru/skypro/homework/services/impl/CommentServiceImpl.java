package ru.skypro.homework.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dtos.CommentDto;
import ru.skypro.homework.mappers.CommentMapper;
import ru.skypro.homework.models.Comment;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.services.CommentService;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    @Override
    public Collection<CommentDto> getComments(Long id) {
        Collection<Comment> comments = commentRepository.findCommentsByAds_Id(id);
        log.info("Get all comments for ad: " + id);
        return CommentMapper.INSTANCE.commentCollectionToCommentDto(comments);
    }

    @Override
    public CommentDto addComment(Long id, CommentDto commentDto, Authentication authentication) {
        if (!adsRepository.existsById(Math.toIntExact(id))) {
            throw new IllegalArgumentException("Ad not found");
        }
        Comment newComment = CommentMapper.INSTANCE.commentDtoToComment(commentDto);
        log.info("Save comment: " + newComment);
        newComment.setAds(adsRepository.findById(Math.toIntExact(id)).get());
        newComment.setAuthorId(userRepository.findByEmail(authentication.getName()));
        commentRepository.save(newComment);
        return CommentMapper.INSTANCE.commentToCommentDto(newComment);
    }

    @Override
    public boolean deleteComment(Long adId, Long id) {
        if (!adsRepository.existsById(Math.toIntExact(id))) {
            throw new IllegalArgumentException("Ad not found");
        }
        log.info("Delete comment: " + id);
        commentRepository.deleteById(id);
        return true;
    }

    @Override
    public CommentDto updateComment(Long adId, CommentDto commentDto, Long id, Authentication authentication) {
        if (!adsRepository.existsById(Math.toIntExact(id))) {
            throw new IllegalArgumentException("Ad not found");
        }
        Comment comment = CommentMapper.INSTANCE.commentDtoToComment(commentDto);
        comment.setAuthorId(userRepository.findByEmail(authentication.getName()));
        log.info("Update comment: " + comment);
        return CommentMapper.INSTANCE.commentToCommentDto(commentRepository.save(comment));
    }
}
