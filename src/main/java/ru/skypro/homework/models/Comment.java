package ru.skypro.homework.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "ads_id")
    private Ads pk;
    private String createdAt;
    private String text;
}
