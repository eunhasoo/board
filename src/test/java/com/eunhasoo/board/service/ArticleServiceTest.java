package com.eunhasoo.board.service;

import com.eunhasoo.board.controller.dto.ArticleForm;
import com.eunhasoo.board.domain.Article;
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
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void 게시글_등록() throws Exception {
        // given
        ArticleForm form = makeArticleForm(1);

        // when
        Integer id = articleService.save(form, "admin");
        Article findArticle = articleService.findOne(id);

        // then
        assertEquals(id, findArticle.getId());
        assertEquals(form.getTitle(), findArticle.getTitle());
        assertEquals(form.getUserId(), findArticle.getUserId());
    }

    @Test
    public void 게시글_수정_성공() throws Exception {
        // given
        ArticleForm form = makeArticleForm(1);
        int id = articleService.save(form, "admin");
        Article beforeUpdate = articleService.findOne(id);
        form.setArticleId(id);

        String title = "수정후 제목";
        String body = "수정후 내용";
        int boardId = 3;
        form.setTitle(title);
        form.setBody(body);
        form.setBoardId(boardId);

        // when
        int updateResult = articleService.update(form);
        Article updatedArticle = articleService.findOne(id);

        // then
        assertEquals(updateResult, 1);
        assertEquals(updatedArticle.getTitle(), title);
        assertEquals(updatedArticle.getBody(), body);
        assertEquals(updatedArticle.getBoard().getId(), boardId);
        assertNotEquals("게시판이 변경되면 새로운 글번호가 부여된다.",
                beforeUpdate.getNo(), updatedArticle.getNo());
    }

    public ArticleForm makeArticleForm(int userId) {
        ArticleForm form = new ArticleForm();
        form.setTitle("제목");
        form.setBody("내용");
        form.setBoardId(2);
        form.setUserId(userId);
        return form;
    }

}