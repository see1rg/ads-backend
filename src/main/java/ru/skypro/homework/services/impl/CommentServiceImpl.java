package ru.skypro.homework.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dtos.CommentDto;
import ru.skypro.homework.dtos.ResponseWrapperComment;
import ru.skypro.homework.mappers.CommentMapper;
import ru.skypro.homework.models.Comment;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.services.CommentService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

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
    public Comment findCommentById(Integer id) {
        log.info("Find comment by id: " + id);
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
    }

    @Override
    public ResponseWrapperComment getComments(Integer id) {
        log.info("Get all comments for ad: " + id);
        List<Comment> comments = commentRepository.findCommentsByAds_Id(id);
        ResponseWrapperComment responseWrapperComment = new ResponseWrapperComment();
        responseWrapperComment.setResults(commentMapper.toCommentsListDto(comments));
        return responseWrapperComment;
    }

    @Override
    public CommentDto addComment(Integer id, CommentDto commentDto, Authentication authentication) {
        if (!adsRepository.existsById(id)) {
            throw new IllegalArgumentException("Ad not found");
        }
        commentDto.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
        Comment newComment = commentMapper.commentDtoToComment(commentDto);
        log.info("Save comment: " + newComment);
        newComment.setAds(adsRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Ad not found")));
        newComment.setAuthorId(userRepository.findUserByUsername(authentication.getName()));
        commentRepository.save(newComment);
        return commentMapper.commentToCommentDto(newComment);
    }

    @Override
    public boolean deleteComment(Integer adId, Integer id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            log.info("Comment not found");
            return false;
        }
        log.info("Delete comment: " + id);
        commentRepository.deleteById(id);
        return true;
    }

    @Override
    public CommentDto updateComment(Integer adId, CommentDto commentDto, Integer commentId, Authentication authentication) {
        log.info("Update comment: " + commentDto);
        if (!adsRepository.existsById(adId)) {
            throw new IllegalArgumentException("Ad not found");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("Comment not found"));
        comment.setText(commentDto.getText());
        commentRepository.save(comment);
        return commentMapper.commentToCommentDto(comment);
    }

}
