package ru.skypro.homework.dtos;

import lombok.Data;


@Data
public class CommentDto {

    private Integer author;
    private String authorImage;
    private String authorName;
    private Long createdAt;
    private Integer pk;
    private String text;
}