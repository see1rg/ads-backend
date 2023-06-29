package ru.skypro.homework.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal price;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_profiles_id",
            referencedColumnName = "id", nullable = false)
    private User authorId;

    @ToString.Exclude
    @OneToOne(mappedBy = "ads", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Image image;

    @OneToMany(mappedBy = "ads", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

}
