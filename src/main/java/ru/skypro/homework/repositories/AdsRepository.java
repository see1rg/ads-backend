package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.Ads;

public interface AdsRepository extends JpaRepository<Ads,Integer> {
}
