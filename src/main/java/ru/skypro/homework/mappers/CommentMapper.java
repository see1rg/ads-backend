package ru.skypro.homework.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
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
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toUnixTime")
    @Mapping(target = "authorFirstName", source = "authorId.firstName" )
    CommentDto commentToCommentDto(Comment comment);

    @InheritInverseConfiguration
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toLocalDateTime")
    Comment commentDtoToComment(CommentDto commentDto);

    default Collection<CommentDto> commentCollectionToCommentDto(Collection<Comment> commentCollection) {
        return commentCollection.stream()
                .map(this::commentToCommentDto)
                .collect(Collectors.toList());
    }

    @Named("toUnixTime")
    default long mapLocalDateTimeToUnixTime(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    @Named("toLocalDateTime")
    default LocalDateTime mapUnixTimeToLocalDateTime(long unixTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(unixTime),ZoneOffset.ofHours(-3));
    }
}
