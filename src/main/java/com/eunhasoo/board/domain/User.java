package com.eunhasoo.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private int id;
    private String loginId;
    private String password;
    private String email;
    private String name;
    private String userType; // ADMIN or MEMBER
    private boolean enable;

    private List<Article> articles;
    private List<Message> messages;
    private List<Comment> comments;
}
