package com.eunhasoo.board.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchResult {
    private int id;
    private LocalDateTime createDate;
    private String userName;
    private String title;
    private String body;
    private int viewCount;
}