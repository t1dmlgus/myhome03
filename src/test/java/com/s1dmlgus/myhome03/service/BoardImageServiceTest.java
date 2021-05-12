package com.s1dmlgus.myhome03.service;

import com.s1dmlgus.myhome03.domain.board.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;


//@SpringBootTest
//class BoardImageServiceTest {
//
//
//    @Autowired
//    private BoardImageService boardImageService;
//    @Autowired
//    private BoardRepository boardRepository;
//
//
//    @Test
//    public void 게시물삭제(){
//
//        Long boardId = 66L;
//
//        //boardRepository.findById(boardId);
//
//
//        boardImageService.deleteBoardImages(boardId);
//
//    }
//
//
//}