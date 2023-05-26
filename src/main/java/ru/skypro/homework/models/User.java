package ru.skypro.homework.models;

import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String image;
    @Enumerated(EnumType.STRING)
    private Role role;

}
