package ru.skypro.homework.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.Image;
import ru.skypro.homework.repositories.ImageRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ImageServiceImpl {

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
    /**
     * Возврат картинки во фронт
     */
    public byte[] downloadImage(String id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        byte[] images = null;
        if (imageOptional.isPresent()) {
            images = imageOptional.get().getData();
        }
        return images;
    }

    /**
     * Сохранение картинки
     */
    public String saveImage(MultipartFile file) {
        Image image = new Image();
        try {
            byte[] bytes = file.getBytes();
            image.setData(bytes);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        image.setId(UUID.randomUUID().toString());
        image.setFileSize((int) file.getSize());
        image.setMediaType(file.getContentType());
        Image savedImage = imageRepository.saveAndFlush(image);
        return savedImage.getId();
    }
}