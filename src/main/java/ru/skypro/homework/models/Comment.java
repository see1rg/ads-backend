package ru.skypro.homework.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String createdAt;
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ads_id", nullable = false)
    private Ads ads;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User authorId;
}
