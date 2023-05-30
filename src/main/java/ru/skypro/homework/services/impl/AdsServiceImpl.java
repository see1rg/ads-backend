package ru.skypro.homework.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.mappers.AdsMapper;
import ru.skypro.homework.models.Ads;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.services.AdsService;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;

    public AdsServiceImpl(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }

    @Override
    public Collection<AdsDto> getAllAds() {
        Collection<Ads> ads = adsRepository.findAll();
        log.info("Get all ads: " + ads);
        return AdsMapper.INSTANCE.adsCollectionToAdsDto(ads);
    }

    @Override
    public AdsDto addAd(AdsDto adsDto, MultipartFile image) {
        Ads newAds = AdsMapper.INSTANCE.adsDtoToAds(adsDto);

        log.info("Save ads: " + newAds);
        adsRepository.save(newAds);

//        imageService.saveImage(newAds.getId(),image);
        log.info("Photo have been saved");

        return AdsMapper.INSTANCE.adsToAdsDto(newAds);
    }

    @Override
    public Optional<AdsDto> getAds(Long id) {
        return null;
    }

    @Override
    public void removeAd(Long id) {

    }

    @Override
    public AdsDto updateAds(AdsDto adsDto, Long id) {
        return null;
    }

    @Override
    public AdsDto getMe() {
        return null;
    }

    @Override
    public byte[] updateImage(Long id, MultipartFile image) {
        return new byte[0];
    }
}
