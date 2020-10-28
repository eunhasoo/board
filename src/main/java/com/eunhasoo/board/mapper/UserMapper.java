package com.eunhasoo.board.mapper;

import com.eunhasoo.board.controller.dto.UsersArticle;
import com.eunhasoo.board.controller.dto.UsersComment;
import com.eunhasoo.board.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<Integer> findIdByName(String name);
    User findById(String loginId);
    int save(User user);
    User findByEmail(String email);
    List<UsersArticle> getArticlesById(int userId);
    List<UsersComment> getCommentsById(int userId);
    int findIfExistByNickname(String nickname);
    int findIfExistByLoginId(String loginId);
    int findIfExistByEmail(String email);
    void update(User user);
}