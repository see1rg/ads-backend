package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.models.Ads;
import ru.skypro.homework.models.Comment;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPk(Ads pk);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comments WHERE ads_id = ?1 ", nativeQuery = true)
    void deleteByAdsId(int id);

}

