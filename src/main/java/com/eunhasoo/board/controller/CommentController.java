package com.eunhasoo.board.controller;

import com.eunhasoo.board.domain.Comment;
import com.eunhasoo.board.domain.Role;
import com.eunhasoo.board.domain.User;
import com.eunhasoo.board.exception.UnAuthorizedException;
import com.eunhasoo.board.mapper.UserMapper;
import com.eunhasoo.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final UserMapper userMapper;
    private final CommentService commentService;

    @PostMapping("/comment/new/{articleId}")
    public String saveComment(@PathVariable int articleId, Comment comment, Principal principal) {
        User user = userMapper.findById(principal.getName());
        comment.setArticleId(articleId);
        comment.setUserId(user.getId());
        commentService.save(comment);
        return "redirect:/article/" + articleId;
    }

    @PostMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable int id, Authentication authentication, @RequestParam int articleId) {
        if (isAdminOrValidUser(authentication)) {
            commentService.deleteById(id);
        } else {
            throw new UnAuthorizedException("접근 권한이 없습니다.");
        }

        return "redirect:/article/" + articleId;
    }

    @PostMapping("/comment/edit/{id}")
    public String editComment(@PathVariable int id, Authentication authentication, Comment comment) {
        if (isAdminOrValidUser(authentication)) {
            commentService.update(comment);
        } else {
            throw new UnAuthorizedException("접근 권한이 없습니다.");
        }

        return "redirect:/article/" + comment.getArticleId();
    }

    public boolean isAdminOrValidUser(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ADMIN.getValue()));
        User user = userMapper.findById(authentication.getName());

        return (user.getLoginId().equals(authentication.getName()) || isAdmin);
    }
}
