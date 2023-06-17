package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    User findByUsername(String username);
    User findByEmail(String email);
    Optional<User> findUserByEmail(String username);
}
