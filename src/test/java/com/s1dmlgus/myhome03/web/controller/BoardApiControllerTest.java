//package com.s1dmlgus.myhome03.web.controller;
//
//import com.s1dmlgus.myhome03.domain.board.Board;
//import com.s1dmlgus.myhome03.domain.board.BoardRepository;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Profile;
//import org.springframework.test.annotation.Commit;
//
//import java.util.stream.IntStream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@Profile("test")
//@SpringBootTest
//class BoardApiControllerTest {
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//
//    @Test
//    public void 더미데이터(){
//
//        IntStream.rangeClosed(1, 300).forEach(i->{
//
//            Board board = Board.builder()
//                    .title("타이틀.." + i)
//                    .content("내용.." + i)
//                    .build();
//
////            System.out.println(board.getTitle());
////            System.out.println(board.getContent());
//
//            System.out.println(boardRepository.save(board));
//        });
//
//    }
//
//
//}