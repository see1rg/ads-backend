package ru.skypro.homework.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dtos.CommentDto;
import ru.skypro.homework.models.Comment;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "pk", source = "id")
    CommentDto commentToCommentDto (Comment comment);

    @InheritInverseConfiguration
    Comment commentDtoToComment(CommentDto commentDto);

    Collection<CommentDto> commentCollectionToCommentDto(Collection<Comment> commentCollection);
}
