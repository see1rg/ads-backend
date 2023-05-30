package ru.skypro.homework.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.Ads;

import java.util.List;

public interface AdsRepository extends JpaRepository<Ads, Integer> {
    @NonNull
    List<Ads> findAll();
}
