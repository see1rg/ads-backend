package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
