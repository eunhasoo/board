package com.eunhasoo.board.controller;

import com.eunhasoo.board.mapper.UserMapper;
import com.eunhasoo.board.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class SearchController {

    private final ArticleService articleService;
    private final UserMapper userMapper;

}
