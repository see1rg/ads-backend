package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer id);

    @Query(value = "SELECT * FROM users WHERE username = ?1", nativeQuery = true)
    User findUserByUsername(String email);

    Optional<Object> findByPassword(String oldPassword);
}
