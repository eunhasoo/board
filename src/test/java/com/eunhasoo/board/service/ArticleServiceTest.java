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
        ArticleForm form = makeArticleForm();

        // when
        Integer id = articleService.insert(form);
        Article findArticle = articleService.findOne(id);

        // then
        assertEquals(id, findArticle.getId());
        assertEquals(form.getTitle(), findArticle.getTitle());
        assertEquals(form.getUserId(), findArticle.getUserId());
    }

    @Test
    public void 게시글_수정() throws Exception {
        // given
        ArticleForm form = makeArticleForm();
        int id = articleService.insert(form);
        form.setArticleId(id);
        Article beforeUpdate = articleService.findOne(id);

        String title = "수정후 제목";
        String body = "수정후 내용";
        int boardId = 3;
        form.setTitle(title);
        form.setBody(body);
        form.setBoardId(boardId);

        // when
        articleService.update(form);
        Article updatedArticle = articleService.findOne(id);

        // then
        assertEquals(updatedArticle.getTitle(), title);
        assertEquals(updatedArticle.getBody(), body);
        assertEquals(updatedArticle.getBoardId(), boardId);
        assertNotEquals("게시판이 변경되면 새로운 글번호가 부여된다.",
                beforeUpdate.getNo(), updatedArticle.getNo());
    }

    public ArticleForm makeArticleForm() {
        ArticleForm form = new ArticleForm();
        form.setTitle("제목");
        form.setBody("내용");
        form.setBoardId(2);
        form.setUserId(1);
        return form;
    }

}