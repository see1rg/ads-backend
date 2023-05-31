package ru.skypro.homework.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.Ads;
import ru.skypro.homework.models.Image;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.ImageRepository;

import java.io.IOException;

@Service
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;
    private final AdsRepository adsRepository;

    public ImageService(ImageRepository imageRepository, AdsRepository adsRepository) {
        this.imageRepository = imageRepository;
        this.adsRepository = adsRepository;
    }

    public byte[] saveImage(Long id, MultipartFile file) throws IOException {
        log.info("Was invoked method to upload photo to animal {}", id);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        Ads ads = adsRepository.findById(id);
        Image imageToSave = new Image();
        imageToSave.setId(id);
        imageToSave.setAds(ads);
        imageToSave.setPreview(file.getBytes());
        imageRepository.save(imageToSave);
        return imageToSave.getPreview();
    }
}
