package ru.skypro.homework.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.Ads;
import ru.skypro.homework.models.Image;
import ru.skypro.homework.models.User;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.ImageRepository;
import ru.skypro.homework.repositories.UserRepository;

import java.io.IOException;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    public byte[] saveImage(Integer id, MultipartFile file) throws IOException {
        log.info("Was invoked method to upload photo to ads with id {}", id);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ads not found"));
        Image imageToSave = new Image();
        imageToSave.setId(id);
        imageToSave.setAds(ads);
        imageToSave.setPreview(file.getBytes());
        imageToSave.setMediaType(file.getContentType());
        imageToSave.setFileSize(file.getSize());
        imageToSave.setFilePath(file.getOriginalFilename());
//        imageToSave.setUser(userRepository.findById(ads.getAuthorId().getId()).get());
        System.out.println(ads);
        imageRepository.save(imageToSave);
        return imageToSave.getPreview();
    }

    public byte[] saveAvatar(String email, MultipartFile file) throws IOException {
        Integer id = userRepository.findUserByUsername(email).getId();
        log.info("Was invoked method to upload photo to user with id {}", id);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        User user = userRepository.findById(id).get();
        Image imageToSave = new Image();
        imageToSave.setId(id);
        imageToSave.setUser(user);
        imageToSave.setPreview(file.getBytes());
        imageToSave.setMediaType(file.getContentType());
        imageToSave.setFileSize(file.getSize());
        imageToSave.setFilePath(file.getOriginalFilename());
        imageRepository.save(imageToSave);
        return imageToSave.getPreview();
    }

    public byte[] getAvatar(int id) {
        log.info("Was invoked method to get avatar from user with id {}", id);
        Image image = imageRepository.findById(id).get();
        if (isEmpty(image)) {
            throw new IllegalArgumentException("Image not found");
        }
        return imageRepository.findById(id).get().getPreview();
    }
}
