package com.eunhasoo.board.controller;

import com.eunhasoo.board.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final ArticleService articleService;

    @GetMapping("/home")
    public String list(Model model) {
        model.addAttribute("articles", articleService.findAll(0));
        return "list";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

}
