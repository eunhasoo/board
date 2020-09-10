package com.eunhasoo.board.controller.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SearchQueries {
    private String option[] = {"제목+내용", "제목", "작성자"};
    private int idx;
    private String qr;
    private Integer boardId; // 게시판 ID
    private List<Integer> userIdList;
}
