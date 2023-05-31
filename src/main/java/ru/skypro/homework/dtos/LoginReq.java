package ru.skypro.homework.dtos;

import lombok.Data;

@Data
public class LoginReq {
    private String password;
    private String username;

}
