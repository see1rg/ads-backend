package ru.skypro.homework.dtos;

import lombok.Data;

@Data
public class CommentDto {
    private int author;
    private String createdAt;
    private int pk;
    private String text;
//    private int id;
//    private int adId;
//    private int commentId;
}
