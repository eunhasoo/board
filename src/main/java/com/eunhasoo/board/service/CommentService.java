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
    public int save(Comment comment) {
        commentMapper.save(comment);
        return comment.getId();
    }

    public List<Comment> findAll(int articleId) {
        return commentMapper.findAllByArticleId(articleId);
    }

    @Transactional
    public int deleteById(int id) {
        return commentMapper.deleteById(id);
    }

    @Transactional
    public int deleteByIds(List<Integer> ids) {
        return commentMapper.deleteByIds(ids);
    }

    @Transactional
    public int update(Comment comment) {
        return commentMapper.update(comment);
    }
}
