package com.eunhasoo.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Message {
    private Long id;
    private String body;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    private User userFrom;
    private User userTo;
}
