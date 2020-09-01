package com.eunhasoo.board.service;

import com.eunhasoo.board.domain.Board;
import com.eunhasoo.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardMapper boardMapper;

    public List<Board> findAll() {
        return boardMapper.findAll();
    }
}
