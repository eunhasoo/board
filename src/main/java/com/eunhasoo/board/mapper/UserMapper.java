package com.eunhasoo.board.mapper;

import com.eunhasoo.board.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<Integer> findIdByName(String name);
    User findById(String loginId);
    int save(User user);
    User findByEmail(String email);
}
