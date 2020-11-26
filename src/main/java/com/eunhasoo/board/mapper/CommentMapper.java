package com.eunhasoo.board.mapper;

import com.eunhasoo.board.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> findAllByArticleId(int articleId);
    int deleteByArticleId(int articleId);
    int deleteById(int id);
    int deleteByIds(List<Integer> ids);
    int update(Comment comment);
    int count(int articleId);
    int save(Comment comment);
}
