package com.s1dmlgus.myhome03.domain.board;

import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {

    Page<BoardResponseDto> searchPage(BoardSearchCondition condition, Pageable pageable);

    List<Board> newBoard();
}
