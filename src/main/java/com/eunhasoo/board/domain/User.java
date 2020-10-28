package com.eunhasoo.board.domain;

import com.eunhasoo.board.controller.dto.UserForm;
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

    public UserForm toUserForm() {
        UserForm userForm = new UserForm();
        userForm.setEmail(this.getEmail());
        userForm.setName(this.getName());
        userForm.setUsername(this.getLoginId());
        userForm.setPassword(this.getPassword());
        return userForm;
    }
}
