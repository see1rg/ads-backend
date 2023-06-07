package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.models.User;

import java.util.Optional;
import java.util.concurrent.Future;

public interface UserRepository extends JpaRepository<User,Long> {

   Optional<User> findById(Long id);

    Optional<User> findUserByEmailIs(String email);

    Optional<User> findByEmail(String email);

}
