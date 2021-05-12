package com.s1dmlgus.myhome03.domain.reply;

import com.s1dmlgus.myhome03.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {


    public List<Reply> findByBoardId(Long id);

    void deleteByBoardId(Long boardId);
}
