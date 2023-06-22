package ru.skypro.homework.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.models.Ads;
import ru.skypro.homework.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
    @NonNull
    List<Ads> findAll();

    Optional<Ads> findById(Integer id);

    void deleteById(Integer id);

    Collection<Ads> findAllByAuthorId(User authorId);


    Collection<Ads> findByTitleLike(String title);
}
