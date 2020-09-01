package com.eunhasoo.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Comment {
    private int id;
    private String body;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    private Article article;
    private User user;
}
