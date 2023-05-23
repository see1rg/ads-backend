package ru.skypro.homework.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String authorImage;
    private String authorFirstName;
    private String createdAt;
    private Integer authorId;
    private String text;
}
