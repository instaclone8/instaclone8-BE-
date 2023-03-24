package com.example.instaclone.user.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private int status;
    private String message;

    public LoginResponseDto(StatusEnum statusEnum) {
        this.status = statusEnum.statusCode;
        this.message = statusEnum.msg;
    }
}