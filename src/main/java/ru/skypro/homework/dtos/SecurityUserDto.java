package ru.skypro.homework.dtos;

import lombok.Data;

@Data
public class SecurityUserDto {
    private Integer id;
    private String email;
    private String password;
    private Role role;
}
