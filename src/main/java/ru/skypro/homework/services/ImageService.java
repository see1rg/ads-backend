package ru.skypro.homework.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.Image;
import ru.skypro.homework.repositories.ImageRepository;

import java.io.IOException;

@Service
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(Long id, MultipartFile file) throws IOException {
        log.info("Was invoked method to upload photo to animal {}", id);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        Image image = new Image();
        image.setId(id);
        image.setPreview(file.getBytes());
        imageRepository.save(image);
    }
}
