package com.eunhasoo.board.controller;

import com.eunhasoo.board.controller.dto.UserForm;
import com.eunhasoo.board.mapper.UserMapper;
import com.eunhasoo.board.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserDetailService userService;

    @GetMapping("/member/signUp")
    public String signUp(Model model) {
        model.addAttribute("user", new UserForm());
        return "member/signUp";
    }

    @PostMapping("/member/signUp")
    public String signUp(UserForm form, Model model) {
        userService.joinUser(form);
        return "redirect:/";
    }

    @GetMapping("/member/signIn")
    public String signIn(Model model) {
        model.addAttribute("user", new UserForm());
        return "member/signIn";
    }

}
