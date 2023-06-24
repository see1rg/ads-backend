package ru.skypro.homework.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdsDto {

    private Integer author;
    private String image;
    private Long pk;
    private BigDecimal price;
    private String title;

}
