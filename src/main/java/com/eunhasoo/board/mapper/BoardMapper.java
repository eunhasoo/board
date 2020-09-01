package com.eunhasoo.board.mapper;

import com.eunhasoo.board.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> findAll();
}
