package com.eunhasoo.board.controller;

import com.eunhasoo.board.controller.dto.ArticleForm;
import com.eunhasoo.board.controller.dto.SearchQueries;
import com.eunhasoo.board.domain.Article;
import com.eunhasoo.board.domain.Comment;
import com.eunhasoo.board.domain.Role;
import com.eunhasoo.board.exception.UnAuthorizedException;
import com.eunhasoo.board.service.ArticleService;
import com.eunhasoo.board.service.BoardService;
import com.eunhasoo.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/article/{articleId}")
    public String findOne(@PathVariable int articleId, Model model) {
        model.addAttribute("article", articleService.findOne(articleId));
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", commentService.findAll(articleId));
        model.addAttribute("commentCount", commentService.count(articleId));
        return "articles/viewArticle";
    }

    @GetMapping("/article/board/{boardId}")
    public String findByBoard(@PathVariable int boardId, Model model) {
        model.addAttribute("articles", articleService.findAll(boardId));
        return "articles/listArticles";
    }

    @GetMapping("/article/new")
    public String createForm(Model model) {
        model.addAttribute("boards", boardService.findAll());
        model.addAttribute("form", new ArticleForm());
        return "articles/createArticle";
    }

    @PostMapping("/article/new")
    public String createArticle(ArticleForm form, Principal principal) {
        int articleId = articleService.save(form, principal.getName());
        return "redirect:/article/" + articleId;
    }

    @GetMapping("/article/edit/{articleId}")
    public String editForm(@PathVariable int articleId, Model model, Authentication authentication) {
        Article article = getArticle(articleId, authentication);

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
    public String editArticle(@PathVariable int articleId, ArticleForm form, Authentication authentication) {
        if (getArticle(articleId, authentication) != null) {
            articleService.update(form);
        }
        return "redirect:/article/" + articleId;
    }

    @PostMapping("/article/delete/{articleId}")
    public String deleteArticle(@PathVariable int articleId, Authentication authentication) {
        Article article = getArticle(articleId, authentication);
        articleService.delete(article.getId());
        return "redirect:/home";
    }

    @GetMapping("/article/search")
    public String search(@RequestParam(required = false) Integer op, @RequestParam(required = false) Integer bId,
                            @RequestParam String qr, Model model) {
        SearchQueries queries = new SearchQueries();
        if (bId != null) { // 게시판 ID
            queries.setBoardId(bId);
        }
        if (op != null) { // 제목+내용 / 제목 / 작성자 중 선택
            queries.setIdx(op);
        }
        queries.setQr(qr); // 검색 쿼리 (필수)

        model.addAttribute("boards", boardService.findAll());
        model.addAttribute("articles", articleService.findByQueries(queries));
        model.addAttribute("queries", queries);
        return "articles/searchArticle";
    }

    public Article getArticle(int articleId, Authentication authentication) {
        Article article = articleService.findOne(articleId);
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ADMIN.getValue()));
        if (!isAdmin) {
            if (!authentication.getName().equals(article.getUser().getLoginId())) {
                throw new UnAuthorizedException("접근 권한이 없습니다.");
            }
        }
        return article;
    }

}
