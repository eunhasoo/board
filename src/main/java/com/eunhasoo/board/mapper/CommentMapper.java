package com.eunhasoo.board.mapper;

import com.eunhasoo.board.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> findAllByArticleId(int articleId);
    int count(int articleId);
}
