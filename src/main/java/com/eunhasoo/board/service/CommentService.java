package com.eunhasoo.board.service;

import com.eunhasoo.board.domain.Comment;
import com.eunhasoo.board.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public Integer count(int articleId) {
        Integer count = commentMapper.count(articleId);
        return (count != null) ? count : 0;
    }

    @Transactional
    public int deleteByArticleId(int articleId) {
        return commentMapper.deleteByArticleId(articleId);
    }

    @Transactional
    public int save(Comment comment) {
        return commentMapper.save(comment);
    }

    public List<Comment> findAll(int articleId) {
        return commentMapper.findAllByArticleId(articleId);
    }

}
