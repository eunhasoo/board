package com.eunhasoo.board.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserForm {
    @NotNull
    @NotEmpty
    private String username; // naming rule

    @NotNull
    @NotEmpty
    private String password; // naming rule

    private String name;
    private String email;
}