package ru.skypro.homework.services;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;

import java.util.Collection;

public interface AdsService {

    public Collection<AdsDto> getAllAds();

    public AdsDto addAd(AdsDto adsDto, MultipartFile image);

    public AdsDto getAds(Long id);

    public void removeAd(Long id);

    public AdsDto updateAds(AdsDto adsDto, Long id);

    public AdsDto getMe();

    public byte[] updateImage(Long id, MultipartFile image);

}
