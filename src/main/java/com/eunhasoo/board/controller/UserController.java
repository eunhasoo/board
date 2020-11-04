package com.eunhasoo.board.controller;

import com.eunhasoo.board.controller.dto.UserForm;
import com.eunhasoo.board.controller.dto.UsersArticle;
import com.eunhasoo.board.controller.dto.UsersComment;
import com.eunhasoo.board.domain.User;
import com.eunhasoo.board.service.UserDetailService;
import com.eunhasoo.board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    // ========== 회원가입 및 로그인 ========== //

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
            return "member/signUp";
        } else if (userDetailService.findUserByEmail(form.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "이미 존재하는 이메일입니다.");
            return "member/signUp";
        } else if (userService.isExistNickname(form.getName())) {
            bindingResult.rejectValue("name", "error.name", "이미 존재하는 닉네임입니다.");
            return "member/signUp";
        } else if (bindingResult.hasErrors()) {
            return "member/signUp";
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

    // ========== 중복 체크 Web API ========== //

    @GetMapping("/member/check/username/{loginId}")
    @ResponseBody
    public int userIdCheck(@PathVariable String loginId) {
        return userService.isExistLoginId(loginId) ? 1 : 0;
    }

    @GetMapping("/member/check/email/{email}")
    @ResponseBody
    public int EmailCheck(@PathVariable String email) {
        return userService.isExistEmail(email) ? 1 : 0;
    }

    @GetMapping("/member/check/name/{nickname}")
    @ResponseBody
    public int nicknameCheck(@PathVariable String nickname) {
        return userService.isExistNickname(nickname) ? 1 : 0;
    }

    // ========== 회원 정보 조회 및 수정 ========== //

    @GetMapping("/member/myInfo")
    public String myInfo() {
        return "member/myInfo";
    }

    @GetMapping("/member/myInfo/articles")
    public String myInfoArticles(Authentication authentication, Model model) {
        List<UsersArticle> usersArticle = userService.getUsersArticle(authentication.getName());
        model.addAttribute("articles", usersArticle);
        return "member/articles";
    }

    @GetMapping("/member/myInfo/comments")
    public String myInfoComments(Authentication authentication, Model model) {
        List<UsersComment> usersComments = userService.getUsersComment(authentication.getName());
        model.addAttribute("comments", usersComments);
        return "member/comments";
    }

    @GetMapping("/member/myInfo/edit")
    public String myInfoEdit(Authentication authentication, Model model) {
        User user = userDetailService.findUserById(authentication.getName());
        model.addAttribute("userForm", user.toUserForm());
        return "member/edit";
    }

    @PostMapping("/member/myInfo/edit")
    public String myInfoEdit(UserForm userForm, BindingResult bindingResult) {
        if (userForm.getPassword().length() > 0 && userForm.getPassword().length() < 6) {
            bindingResult.rejectValue("password", "error.password", "6자 이상으로 입력해주세요.");
            return "member/edit";
        }

        if (!userForm.getNameBeforeChanged().equals(userForm.getName())) {
            if (userService.isExistNickname(userForm.getName())) {
                bindingResult.rejectValue("name", "error.name", "이미 존재하는 닉네임입니다.");
                return "member/edit";
            }

            if (userForm.getName().length() < 2 || userForm.getName().length() > 12) {
                bindingResult.rejectValue("name", "error.name", "2자 이상 12자 이내로 입력해주세요.");
                return "member/edit";
            }
        }

        userService.updateUser(userForm.toUser());
        return "member/myInfo";
    }

}
