package ru.skypro.homework.dtos;

import lombok.Data;

@Data
public class AdsDto {
    private int author;
    private String image;
    private int pk;
    private int price;
    private String title;
    private String description;
}
