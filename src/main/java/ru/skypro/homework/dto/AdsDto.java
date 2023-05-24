package ru.skypro.homework.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdsDto {
    private Integer id;
    private String image;
    private BigDecimal price;
    private String title;
    private Integer authorId;
}
