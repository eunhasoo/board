package com.eunhasoo.board.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsersComment {
    private int id;
    private String body;
    private String title;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
}
