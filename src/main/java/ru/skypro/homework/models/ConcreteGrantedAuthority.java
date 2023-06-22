package ru.skypro.homework.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Представляет конкретную реализацию интерфейса {@link GrantedAuthority},
 * представляет разрешение, которое может быть присвоено пользователю в системе.
 * Разрешение обычно представляет собой строку, которая определяет определенные права доступа
 * или привилегии в рамках системы.
 */
@Entity
@Getter
@Setter
public class ConcreteGrantedAuthority implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Long id;
    private String authority;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ConcreteGrantedAuthority() {

    }

    public ConcreteGrantedAuthority(String authority) {
        this.authority = authority;
    }
}

