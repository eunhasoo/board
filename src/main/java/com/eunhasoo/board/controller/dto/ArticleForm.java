package com.eunhasoo.board.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleForm {
    private int userId;
    private int articleId;
    private int boardId;
    private String title;
    private String body;
}
