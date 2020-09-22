package com.eunhasoo.board.service;

import com.eunhasoo.board.controller.dto.ArticleForm;
import com.eunhasoo.board.controller.dto.SearchQueries;
import com.eunhasoo.board.controller.dto.SearchResult;
import com.eunhasoo.board.domain.Article;
import com.eunhasoo.board.domain.User;
import com.eunhasoo.board.mapper.ArticleMapper;
import com.eunhasoo.board.mapper.CommentMapper;
import com.eunhasoo.board.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ArticleService {

    private final ArticleMapper articleMapper;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;

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
    public int save(ArticleForm form, String loginId) {
        User user = userMapper.findById(loginId);
        Article article = new Article();
        article.setBody(form.getBody());
        article.setTitle(form.getTitle());
        article.setUserId(user.getId());
        article.setBoardId(form.getBoardId());
        article.setNo(findLastAddedNo(form.getBoardId()));
        articleMapper.save(article);
        return article.getId();
    }

    @Transactional
    public int delete(int articleId) {
        commentMapper.deleteByArticleId(articleId);
        return articleMapper.delete(articleId);
    }

    @Transactional
    public int update(ArticleForm form) {
        Article origin = articleMapper.findOne(form.getArticleId());
        // 게시판이 변경되면 새로운 글번호를 부여한다
        if (origin.getBoard().getId() != form.getBoardId()) {
            form.setNo(findLastAddedNo(form.getBoardId()));
        } else {
            form.setNo(0);
        }
        return articleMapper.update(form);
    }

    public List<SearchResult> findByQueries(SearchQueries query) {
        Integer idx = query.getIdx();
        if (idx == 2) { // 작성자명으로 쿼리 검색시
            List<Integer> userIdList = userMapper.findIdByName(query.getQr());
            if (userIdList.size() == 0) { // 결과가 없으면 빈 리스트를 리턴한다
                return new ArrayList<>();
            } else {
                query.setUserIdList(userIdList);
            }
        }
        return articleMapper.findByQueries(query);
    }

}
