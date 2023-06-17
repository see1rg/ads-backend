package ru.skypro.homework.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ads_id", unique = true)
    private int pk;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    private int price;
    private String image;
    private String title;
    private String description;
}
