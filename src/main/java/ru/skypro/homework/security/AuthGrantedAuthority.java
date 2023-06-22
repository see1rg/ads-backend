package ru.skypro.homework.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import ru.skypro.homework.models.User;

import javax.persistence.*;

@Entity
@ToString
@RequiredArgsConstructor
@Data
@Table(name = "authorities")
public class AuthGrantedAuthority implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Long id;
    private String authority;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
