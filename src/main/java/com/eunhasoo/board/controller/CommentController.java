package com.eunhasoo.board.controller;

import com.eunhasoo.board.domain.Comment;
import com.eunhasoo.board.domain.User;
import com.eunhasoo.board.mapper.UserMapper;
import com.eunhasoo.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

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

}
