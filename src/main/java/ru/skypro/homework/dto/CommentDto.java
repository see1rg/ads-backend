package ru.skypro.homework.dto;

import lombok.Data;


@Data

public class CommentDto {

    private Integer id;
    private String authorImage;
    private String authorFirstName;
    private String createdAt;
    private Integer authorId;
    private String text;
}