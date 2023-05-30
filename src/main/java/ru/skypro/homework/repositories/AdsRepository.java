package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.Ads;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AdsRepository extends JpaRepository<Ads, Integer> {
    @NotNull
    List<Ads> findAll();
}
