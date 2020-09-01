package com.eunhasoo.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Board {
    private int id;
    private String boardName;

    private List<Article> articles;
}