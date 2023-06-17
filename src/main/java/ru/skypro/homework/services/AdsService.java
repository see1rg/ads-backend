package ru.skypro.homework.services;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dtos.AdsDto;

import java.io.IOException;
import java.util.Collection;

public interface AdsService {

    Iterable<AdsDto> getAllAds();

    AdsDto addAd(AdsDto adsDto, MultipartFile image) throws IOException;

    AdsDto getAds(Integer id);

    boolean removeAd(Integer id);

    AdsDto updateAds(AdsDto adsDto, Integer id);

    Collection<AdsDto> getMe(String email);

    byte[] updateImage(Integer id, MultipartFile image) throws IOException;

}
