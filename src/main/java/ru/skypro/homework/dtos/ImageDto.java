package ru.skypro.homework.dtos;

import lombok.Data;

@Data
public class ImageDto {
    private Long pk;
    private Long idAds;
    private Integer fileSize;
    private String mediaType;
}