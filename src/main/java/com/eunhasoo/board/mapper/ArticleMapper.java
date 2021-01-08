package com.eunhasoo.board.mapper;

import com.eunhasoo.board.controller.dto.ArticleForm;
import com.eunhasoo.board.controller.dto.SearchQueries;
import com.eunhasoo.board.controller.dto.SearchResult;
import com.eunhasoo.board.domain.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    Article findOne(int articleId);
    List<Article> findAll(int boardId);
    Integer findLastAddedNo(int boardId);
    Integer save(Article article);
    int update(ArticleForm form);
    int delete(int articleId);
    List<SearchResult> findByQueries(SearchQueries query);
    void countView(int articleId);
}
