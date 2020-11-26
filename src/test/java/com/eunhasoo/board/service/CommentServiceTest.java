package com.eunhasoo.board.service;

import com.eunhasoo.board.domain.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Test
    public void 코멘트_삭제() throws Exception {
        // given
        Comment comment = new Comment();
        int userId = 1;
        comment.setUserId(userId);
        comment.setBody("내용");
        comment.setArticleId(129);
        int id = commentService.save(comment);

        // when
        int result = commentService.deleteById(id);

        // then
        assertEquals("댓글 삭제 성공", result, 1);
    }

}