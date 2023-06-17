package ru.skypro.homework.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAdsDto {
    private int count;
    private List<AdsDto> results;
}
