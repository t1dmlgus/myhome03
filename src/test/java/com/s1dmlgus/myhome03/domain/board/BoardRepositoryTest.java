package com.s1dmlgus.myhome03.domain.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardSearchCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;


@Transactional
@SpringBootTest
class BoardRepositoryTest {


    @Autowired
    EntityManager em;
    @Autowired
    BoardRepository boardRepository;


    JPAQueryFactory jpaQueryFactory;


    Member member01;
    Member member02;
    Member member03;

    Board board01;
    Board board02;
    Board board03;
    Board board04;
    Board board05;

    @BeforeEach
    public void init(){

        jpaQueryFactory = new JPAQueryFactory(em);



//        member01= new Member("member01", 20);
//        member02 = new Member("member02", 30);
//        member03 = new Member("member03", 40);

//        em.persist(member01);
//        em.persist(member02);
        //em.persist(member03);


        board01 = new Board("board01", "boardCCCCONTENT01", member01);
        board02 = new Board("board02", "boardCCCCONTENT02", member01);
        board03 = new Board("board03", "boardCCCCONTENT03", member01);
        board04 = new Board("board04", "boardCCCCONTENT04", member02);
        board05 = new Board("board05", "boardCCCCONTENT04", member02);

        em.persist(board01);
        em.persist(board02);
        em.persist(board03);
        em.persist(board04);
        em.persist(board05);



    }

    @Test
    public void basicTest() throws Exception{
        //given

        //검색조건
        BoardSearchCondition condition = new BoardSearchCondition();
        PageRequest pagealbe = PageRequest.of(0, 3);


        //when

        Page<BoardResponseDto> result = boardRepository.searchPage(condition, pagealbe);


        for (BoardResponseDto boardResponseDto : result) {
            System.out.println("boardResponseDto = " + boardResponseDto);

        }

        Pageable pageable = result.getPageable();
        List<BoardResponseDto> content = result.getContent();
        long totalElements = result.getTotalElements();
        int totalPages = result.getTotalPages();

        System.out.println("pageable = " + pageable);
        System.out.println("content = " + content);
        System.out.println("totalElements = " + totalElements);
        System.out.println("totalPages = " + totalPages);





        //then


    }

    @Test
    public void 게시물find(){

        List<Board> all = boardRepository.findAll();

        for (Board board : all) {
            System.out.println("board = " + board);

        }




    }

}