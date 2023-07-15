package ru.skypro.homework.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.Ads;
import ru.skypro.homework.models.Image;
import ru.skypro.homework.models.User;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.ImageRepository;
import ru.skypro.homework.repositories.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    @Value("${Image.dir.path}")
    private String imageDir;

    public void saveImage(Integer id, MultipartFile file) throws IOException {
        log.info("Was invoked method to upload photo to ads with id {}", id);
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ads not found"));
        if (isEmpty(file)) {
            throw new IllegalArgumentException("File is empty");
        }
        if (!adsRepository.existsById(id)) {
            throw new IllegalArgumentException("Ads not found");
        }
        Image imageToSave = imageRepository.findByAds(ads);
        if (imageToSave == null) {
            imageToSave = new Image();
        } else {
            String filePath = ads.getImage().getFilePath();
            File fileToDelete = new File(filePath);
            try {
                Files.deleteIfExists(fileToDelete.toPath());
            } catch (IOException ex) {
                log.error("Failed to delete file: {}", ex.getMessage());
            }
        }
        imageToSave.setAds(ads);
        saveImageAndGetBytes(file, imageToSave);
    }

    public void saveAvatar(String email, MultipartFile file) throws IOException {
        Integer id = userRepository.findUserByUsername(email).getId();
        log.info("Was invoked method to upload photo to user with id {}", id);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Image imageToSave = imageRepository.findByUser(user);
        if (imageToSave == null) {
            imageToSave = new Image();
        } else {
            String filePath = user.getAvatar().getFilePath();
            File fileToDelete = new File(filePath);
            try {
                Files.deleteIfExists(fileToDelete.toPath());
            } catch (IOException ex) {
                log.error("Failed to delete file: {}", ex.getMessage());
            }
        }
        imageToSave.setUser(user);
        saveImageAndGetBytes(file, imageToSave);
    }

    private void saveImageAndGetBytes(MultipartFile file, Image imageToSave) throws IOException {
        imageToSave.setPreview(file.getBytes());
        imageToSave.setMediaType(file.getContentType());
        imageToSave.setFileSize(file.getSize());
        String path = uploadImage(UUID.randomUUID().toString(), file);
        imageToSave.setFilePath(path);
        try {
            imageRepository.save(imageToSave);
        } catch (Exception ex) {
            log.error("Failed to save image", ex);
            throw new RuntimeException("Was not possible to save image");
        }
    }

    public String uploadImage(String name, MultipartFile file) {
        log.debug("Was invoked method to upload image");


        String extension = Optional.ofNullable(StringUtils.getFilenameExtension(file.getOriginalFilename())).
                orElse(" ");

        Path filePath = Path.of(imageDir, name + extension);
        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            Files.write(filePath, file.getBytes());
        } catch (IOException ex) {
            log.error("Filed to delete file", ex);
            throw new RuntimeException("Was not possible to delete image");
        }
        return filePath.toString();
    }

    public byte[] getAvatar(int id) throws IOException {
        log.info("Was invoked method to get avatar from user with id {}", id);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User user = userOptional.get();
        Image image = user.getAvatar();
        if (image == null) {
            return null;
        }
        String filePath = image.getFilePath();
        if (filePath == null) {
            return null;
        }
        return Files.readAllBytes(Paths.get(filePath));
    }

    public byte[] getImage(int id) throws IOException { //for AdsMapper
        log.info("Was invoked method to get image from ads with id {}", id);
        Optional<Ads> adsOptional = adsRepository.findById(id);
        if (adsOptional.isEmpty()) {
            throw new IllegalArgumentException("Ads not found");
        }
        Ads ads = adsOptional.get();
        Image image = ads.getImage();
        if (image == null) {
            return null;
        }
        String filePath = image.getFilePath();
        return Files.readAllBytes(Paths.get(filePath));
    }
}