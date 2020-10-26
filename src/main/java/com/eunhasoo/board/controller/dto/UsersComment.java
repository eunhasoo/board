package com.eunhasoo.board.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsersComment {
    private int id;
    private int articleId;
    private String body;
    private String articleTitle;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
}
