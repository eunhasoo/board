package com.eunhasoo.board.controller;

import com.eunhasoo.board.controller.dto.UserForm;
import com.eunhasoo.board.controller.dto.UsersArticle;
import com.eunhasoo.board.controller.dto.UsersComment;
import com.eunhasoo.board.service.UserDetailService;
import com.eunhasoo.board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    private final UserDetailService userDetailService;
    private final UserService userService;

    @GetMapping("/member/signUp")
    public String signUp(Model model) {
        model.addAttribute("user", new UserForm());
        return "member/signUp";
    }

    @PostMapping("/member/signUp")
    public String signUp(@ModelAttribute("user") @Valid UserForm form, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (userDetailService.findUserById(form.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.username", "이미 존재하는 아이디입니다.");
            return "/member/signUp";
        } else if (userDetailService.findUserByEmail(form.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "이미 존재하는 이메일입니다.");
            return "/member/signUp";
        } else if (bindingResult.hasErrors()) {
            return "/member/signUp";
        }

        userDetailService.joinUser(form);
        redirectAttributes.addFlashAttribute("loginId", form.getUsername());
        redirectAttributes.addFlashAttribute("name", form.getName());
        return "redirect:/member/signUpSuccess";
    }

    @GetMapping("/member/signUpSuccess")
    public String success(Model model) throws Exception {
        String loginId = (String) model.asMap().get("loginId");
        String name = (String) model.asMap().get("name");
        if (loginId == null || name == null) {
            throw new Exception("잘못된 접근입니다.");
        }
        model.addAttribute("loginId", loginId);
        model.addAttribute("name", name);
        return "member/signUpSuccess";
    }

    @GetMapping("/member/signIn")
    public String signIn(Model model, HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        String uri = request.getRequestURI();
        if (!uri.equals("/member/signUp") && !uri.equals("/member/signUpSuccess")
                && !uri.equals("/member/signIn")) {
            request.getSession().setAttribute("prevPage", referrer);
        }
        model.addAttribute("user", new UserForm());
        return "member/signIn";
    }

    @GetMapping("/member/myInfo")
    public String myInfo() {
        return "member/myInfo";
    }

    @GetMapping("/member/myInfo/articles")
    public String userArticles(Authentication authentication, Model model) {
        List<UsersArticle> usersArticle = userService.getUsersArticle(authentication.getName());
        model.addAttribute("articles", usersArticle);
        return "member/articles";
    }

    @GetMapping("/member/myInfo/comments")
    public String userComments(Authentication authentication, Model model) {
        List<UsersComment> usersComments = userService.getUsersComment(authentication.getName());
        model.addAttribute("comments", usersComments);
        return "member/comments";
    }


}
