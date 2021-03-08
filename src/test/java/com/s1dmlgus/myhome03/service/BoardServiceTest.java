package com.s1dmlgus.myhome03.service;

import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.board.BoardRepository;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.web.dto.board.BoardRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;


@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PrincipalDetails principalDetails;

    @Commit
    @Test
    public void 게시물등록테스트() throws Exception{
        //given

        Member t1dmlgus = Member.builder()
                .username("t1dmlgus")
                .age(20)
                .build();

        //BoardRequestDto dto = new BoardRequestDto("하이", "바이",t1dmlgus);

        //when
        //Board board = dto.toEntity();
        //boardRepository.save(board);

        //then
    }


}