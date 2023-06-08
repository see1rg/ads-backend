package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.Comment;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @NotNull
    List<Comment> findAll();

    Collection<Comment> findCommentsByAds_Id(Long id);

    void deleteById(Long id);

}
