package com.eunhasoo.board.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsersArticle {
    private int id;
    private String title;
    private String body;
    private LocalDateTime createDate;
}
