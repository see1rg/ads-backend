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

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public Collection<CommentDto> getComments(Integer id) {
        Collection<Comment> comments = commentRepository.findCommentsByAds_Id(id);
        log.info("Get all comments for ad: " + id);
        return commentMapper.commentCollectionToCommentDto(comments);
    }

    @Override
    public CommentDto addComment(Integer id, CommentDto commentDto, Authentication authentication) {
        if (!adsRepository.existsById(id)) {
            throw new IllegalArgumentException("Ad not found");
        }
        Comment newComment = commentMapper.commentDtoToComment(commentDto);
        log.info("Save comment: " + newComment);
        newComment.setAds(adsRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Ad not found")));
        newComment.setAuthorId(userRepository.findByEmail(authentication.getName()));
        commentRepository.save(newComment);
        return commentMapper.commentToCommentDto(newComment);
    }

    @Override
    public boolean deleteComment(Integer adId, Integer id) {
        if (!adsRepository.existsById(adId)) {
            throw new IllegalArgumentException("Ad not found");
        }
        log.info("Delete comment: " + id);
        commentRepository.deleteById(id);
        return true;
    }

    @Override
    public CommentDto updateComment(Integer adId, CommentDto commentDto, Integer id, Authentication authentication) {
        if (!adsRepository.existsById(adId)) {
            throw new IllegalArgumentException("Ad not found");
        }
        Comment comment = commentMapper.commentDtoToComment(commentDto);
        comment.setAuthorId(userRepository.findByEmail(authentication.getName()));
        log.info("Update comment: " + comment);
        return commentMapper.commentToCommentDto(commentRepository.save(comment));
    }
}
