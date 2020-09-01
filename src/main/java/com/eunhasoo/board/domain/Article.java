package com.eunhasoo.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Article {

    private Integer id;
    private int no;
    private String title;
    private String body;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    private int userId;
    private User user;

    private int boardId;
    private Board board;

}
