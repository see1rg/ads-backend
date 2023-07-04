package ru.skypro.homework.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dtos.CommentDto;
import ru.skypro.homework.models.Comment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "createdAt", expression = "java(mapLocalDateTimeToUnixTime(comment.getCreatedAt()))")
    @Mapping(target = "authorName", source = "authorId.firstName")
    @Mapping(target = "authorImage", expression = "java(image(comment))")
    CommentDto commentToCommentDto(Comment comment);
    default String image(Comment comment) {
        int id = comment.getAuthorId().getId();
        return "/users/" + id + "/image";
    }

    @InheritInverseConfiguration
    @Mapping(target = "createdAt", expression = "java(mapUnixTimeToLocalDateTime(commentDto.getCreatedAt()))")
    Comment commentDtoToComment(CommentDto commentDto);

    default Collection<CommentDto> commentCollectionToCommentDto(Collection<Comment> commentCollection) {
        return commentCollection.stream()
                .map(this::commentToCommentDto)
                .collect(Collectors.toList());
    }

    default long mapLocalDateTimeToUnixTime(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    default LocalDateTime mapUnixTimeToLocalDateTime(long unixTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(unixTime), ZoneOffset.ofHours(-3));
    }
}
