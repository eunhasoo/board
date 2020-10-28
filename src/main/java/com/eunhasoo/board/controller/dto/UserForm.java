package com.eunhasoo.board.controller.dto;

import com.eunhasoo.board.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserForm {
    @NotEmpty(message="아이디를 입력해주세요.")
    @Size(min=6, max=20, message="6자 이상 20자 이내로 작성해주세요.")
    private String username; // naming rule

    @NotEmpty(message="비밀번호를 입력해주세요.")
    @Size(min=6, message="6자 이상으로 작성해주세요.")
    private String password; // naming rule

    @NotEmpty(message="닉네임을 작성해주세요.")
    @Size(min=2, max=12, message="2자 이상 12자 이내로 작성해주세요.")
    private String name;

    @NotEmpty(message="이메일을 작성해주세요.")
    @Email(message="이메일 주소를 양식에 맞게 작성해주세요.")
    private String email;

    public User toUser() {
        User user = new User();
        user.setLoginId(this.getUsername());
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        user.setName(this.getName());
        return user;
    }
}