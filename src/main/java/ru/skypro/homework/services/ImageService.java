package ru.skypro.homework.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.Ads;
import ru.skypro.homework.models.Image;
import ru.skypro.homework.models.User;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.ImageRepository;
import ru.skypro.homework.repositories.UserRepository;

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

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Image imageToSave = imageRepository.findByUser(user);
        if (imageToSave == null) {
            imageToSave = new Image();
        } else {
            String filePath = user.getAvatar().getFilePath();
            Path fileToDelete = Paths.get(filePath);
            if (Files.exists(fileToDelete)) {
                try {
                    Files.delete(fileToDelete);
                } catch (IOException e) {
                    throw new IOException("Failed to delete existing file", e);
                }
            }
        }

        imageToSave.setUser(user);

        byte[] savedImageBytes = saveImageAndGetBytes(file, imageToSave);
        if (savedImageBytes == null || savedImageBytes.length == 0) {
            throw new IOException("Failed to save image");
        }

    }


    private byte[] saveImageAndGetBytes(MultipartFile file, Image imageToSave) throws IOException {
        imageToSave.setPreview(file.getBytes());
        imageToSave.setMediaType(file.getContentType());
        imageToSave.setFileSize(file.getSize());
        String path = uploadImage(UUID.randomUUID().toString(), file);
        imageToSave.setFilePath(path);
        imageRepository.save(imageToSave);
        return Files.readAllBytes(Paths.get(path));
    }

    public String uploadImage(String name, MultipartFile file) {
        log.debug("Was invoked method to upload image");

        String extension = Optional.ofNullable(file.getOriginalFilename()).
                map(s -> s.substring(file.getOriginalFilename().lastIndexOf("."))).
                orElse(" ");

        Path filePath = Path.of(imageDir, name + extension);
        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            Files.write(filePath, file.getBytes());
        } catch (IOException ex) {
            log.error("Picture not saved", ex);
            throw new RuntimeException("Возникла проблема при создании или записи файла или директории.");
        }
        return filePath.toString();
    }

    public byte[] getAvatar(int id) throws IOException {
        log.info("Was invoked method to get avatar from user with id {}", id);
        Optional<User> userOptional = userRepository.findById(id);

        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));
        Image avatar = user.getAvatar();

        if (avatar == null || avatar.getFilePath() == null) {
            return new byte[0];
        }

        try {
            return Files.readAllBytes(Paths.get(avatar.getFilePath()));
        } catch (IOException e) {
            throw new IOException("Failed to read avatar image", e);
        }
    }

    public byte[] getImage(int id) throws IOException {
        log.info("Was invoked method to get image from ads with id {}", id);
        Optional<Ads> adsOptional = adsRepository.findById(id);

        Ads ads = adsOptional.orElseThrow(() -> new IllegalArgumentException("Ads not found"));
        Image image = ads.getImage();

        if (image == null || image.getFilePath() == null) {
            return new byte[0];
        }

        try {
            return Files.readAllBytes(Paths.get(image.getFilePath()));
        } catch (IOException e) {
            throw new IOException("Failed to read image", e);
        }
    }

}