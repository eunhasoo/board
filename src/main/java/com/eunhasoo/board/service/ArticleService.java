package com.eunhasoo.board.service;

import com.eunhasoo.board.controller.dto.ArticleForm;
import com.eunhasoo.board.controller.dto.SearchQueries;
import com.eunhasoo.board.controller.dto.SearchResult;
import com.eunhasoo.board.domain.Article;
import com.eunhasoo.board.mapper.ArticleMapper;
import com.eunhasoo.board.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ArticleService {

    private final ArticleMapper articleMapper;
    private final UserMapper userMapper;

    public List<Article> findAll(int boardId) {
        return articleMapper.findAll(boardId);
    }

    public Article findOne(int articleId) {
        return articleMapper.findOne(articleId);
    }

    public int findLastAddedNo(int boardId) {
        Integer last = articleMapper.findLastAddedNo(boardId);
        return (last != null) ? (last + 1) : 1;
    }

    @Transactional
    public int save(ArticleForm form) {
        Article article = new Article();
        article.setBody(form.getBody());
        article.setTitle(form.getTitle());
        article.setUserId(form.getUserId());
        article.setBoardId(form.getBoardId());
        article.setNo(articleMapper.findLastAddedNo(form.getBoardId()));
        articleMapper.save(article);
        return article.getId();
    }

    @Transactional
    public void delete(int articleId) {
        articleMapper.delete(articleId);
    }

    @Transactional
    public void update(ArticleForm form) {
        Article origin = articleMapper.findOne(form.getArticleId());
        Integer no = null, boardId = null;
        // 게시판이 변경되면 새로운 글번호가 부여된다
        if (origin.getBoard().getId() != form.getBoardId()) {
            no = findLastAddedNo(form.getBoardId());
            boardId = form.getBoardId();
        }
        articleMapper.update(boardId, no, form.getTitle(), form.getBody(), form.getArticleId());
    }

    public List<SearchResult> findByQueries(SearchQueries query) {
        Integer idx = query.getIdx();
        if (idx != null && idx == 2) {
            query.setUserIdList(userMapper.findIdByName(query.getQr()));
        }
        return articleMapper.findByQueries(query);
    }

}
