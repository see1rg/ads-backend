package ru.skypro.homework.services;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;

import java.io.IOException;
import java.util.Optional;

public interface AdsService {

    public Iterable<AdsDto> getAllAds();

    public AdsDto addAd(AdsDto adsDto, MultipartFile image) throws IOException;

    public Optional<AdsDto> getAds(Long id);

    public void removeAd(Long id);

    public AdsDto updateAds(AdsDto adsDto, Long id);

    public AdsDto getMe();

    public byte[] updateImage(Long id, MultipartFile image) throws IOException;

}
