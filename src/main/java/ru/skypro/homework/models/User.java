package ru.skypro.homework.models;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.dtos.Role;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.security.AuthGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * Реализация интерфейса {@code UserDetails} предоставляет информацию о пользователе,
 * необходимую для аутентификации и авторизации в системе. Включает методы, возвращающие
 * различные атрибуты пользователя, такие как имя пользователя, пароль, разрешения и т.д.
 */
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;
    @Column(name = "username")
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "user")
    @ToStringExclude
    private Image avatar;
    private String password;
    private boolean accountExpired;
    private boolean credentialsExpired;
    private boolean accountLocked;
    private boolean enabled;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @ToStringExclude
    private Set<AuthGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
