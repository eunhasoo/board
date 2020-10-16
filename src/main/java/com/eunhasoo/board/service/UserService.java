package com.eunhasoo.board.service;

import com.eunhasoo.board.controller.dto.UsersArticle;
import com.eunhasoo.board.controller.dto.UsersComment;
import com.eunhasoo.board.domain.User;
import com.eunhasoo.board.exception.UnAuthorizedException;
import com.eunhasoo.board.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    public List<UsersArticle> getUsersArticle(String loginId) {
        User user = userMapper.findById(loginId);
        if (user == null) {
            throw new UnAuthorizedException("없는 회원 정보입니다.");
        }
        List<UsersArticle> usersArticles = userMapper.getArticlesById(user.getId());
        if (usersArticles != null) {
            for (int i = 0; i < usersArticles.size(); i++) {
                UsersArticle usersArticle = usersArticles.get(i);
                usersArticle.setBody(parseHTML(usersArticle.getBody()));
            }
        } else {
            usersArticles = new ArrayList<>();
        }
        return usersArticles;
    }

    public List<UsersComment> getUsersComment(String loginId) {
        User user = userMapper.findById(loginId);
        List<UsersComment> usersComments = userMapper.getCommentsById(user.getId());
        if (usersComments != null) {
            for (int i = 0; i < usersComments.size(); i++) {
                UsersComment usersComment = usersComments.get(i);
                usersComment.setBody(parseHTML(usersComment.getBody()));
            }
        } else {
            usersComments = new ArrayList<>();
        }
        return usersComments;
    }

    public String parseHTML(String resources) {
        Document doc = Jsoup.parse(resources);
        String result = doc.body().text();
        return result.length() > 240 ? result.substring(0, 240) : result;
    }

}
