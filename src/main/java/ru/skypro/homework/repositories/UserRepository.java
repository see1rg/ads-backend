package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);

    User findById(Long id);
}
