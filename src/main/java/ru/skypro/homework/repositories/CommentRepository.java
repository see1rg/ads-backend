package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.models.Comment;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @NotNull
    List<Comment> findAll();

    Collection<Comment> findCommentsByAds_Id(Integer id);

    void deleteById(Integer id);

}
