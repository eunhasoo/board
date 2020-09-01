package com.eunhasoo.board.controller;

import com.eunhasoo.board.controller.dto.ArticleForm;
import com.eunhasoo.board.controller.dto.SearchQueries;
import com.eunhasoo.board.domain.Article;
import com.eunhasoo.board.service.ArticleService;
import com.eunhasoo.board.service.BoardService;
import com.eunhasoo.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/article/{articleId}")
    public String findOne(@PathVariable int articleId, Model model) {
        model.addAttribute("article", articleService.findOne(articleId));
        model.addAttribute("comments", commentService.findAll(articleId));
        model.addAttribute("commentCount", commentService.count(articleId));
        return "articles/viewArticle";
    }

    @GetMapping("/article/board/{boardId}")
    public String findAll(@PathVariable int boardId, Model model) {
        model.addAttribute("articles", articleService.findAll(boardId));
        return "list";
    }

    @GetMapping("/article/new")
    public String createForm(Model model) {
        model.addAttribute("boards", boardService.findAll());
        model.addAttribute("form", new ArticleForm());
        return "articles/createArticle";
    }

    @PostMapping("/article/new")
    public String createArticle(ArticleForm form) {
        int articleId = articleService.save(form);
        return "redirect:/article/" + articleId;
    }

    @GetMapping("/article/edit/{articleId}")
    public String editForm(@PathVariable int articleId, Model model) {
        Article article = articleService.findOne(articleId);

        ArticleForm form = new ArticleForm();
        form.setArticleId(article.getId());
        form.setBoardId(article.getBoard().getId());
        form.setBody(article.getBody());
        form.setTitle(article.getTitle());
        model.addAttribute("form", form);
        model.addAttribute("boards", boardService.findAll());
        return "articles/editArticle";
    }

    @PostMapping("/article/edit/{articleId}")
    public String editArticle(@PathVariable int articleId, ArticleForm form) {
        articleService.update(form);
        return "redirect:/article/" + articleId;
    }

    @PostMapping("/article/delete/{articleId}")
    public String deleteArticle(@PathVariable int articleId) {
        articleService.delete(articleId);
        return "redirect:/home";
    }

    @GetMapping("/article/search")
    public String search(@RequestParam(required = false) Integer op, @RequestParam(required = false) Integer bId,
                            @RequestParam String qr, Model model) {
        SearchQueries queries = new SearchQueries();
        if (bId != null) {
            queries.setBoardId(bId);
        }
        if (op != null) {
            queries.setIdx(op);
        }
        queries.setQr(qr);
        model.addAttribute("boards", boardService.findAll());
        model.addAttribute("articles", articleService.findByQueries(queries));
        model.addAttribute("queries", queries);
        return "articles/searchArticle";
    }

}
