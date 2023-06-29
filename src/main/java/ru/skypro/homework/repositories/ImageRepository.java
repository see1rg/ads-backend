package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.Ads;
import ru.skypro.homework.models.Image;
import ru.skypro.homework.models.User;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findByUser(User user);

    Image findByAds(Ads ads);
}
