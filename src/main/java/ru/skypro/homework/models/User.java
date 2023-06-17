package ru.skypro.homework.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String regDate;
    private String city;
    private String image;
    private String password;
    @OneToMany(mappedBy = "author", cascade=CascadeType.ALL)
    private List<Ads> adsList;
    @OneToMany(mappedBy = "author", cascade=CascadeType.ALL)
    private List<Comment> commentList;
    private String role;
}
