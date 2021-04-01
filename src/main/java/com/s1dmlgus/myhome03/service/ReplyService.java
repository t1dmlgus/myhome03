package com.s1dmlgus.myhome03.service;


import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.board.BoardRepository;
import com.s1dmlgus.myhome03.domain.reply.Reply;
import com.s1dmlgus.myhome03.domain.reply.ReplyRepository;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyRequestDto;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    // 댓글 등록
    @Transactional
    public ReplyResponseDto saveReply(Long id, ReplyRequestDto replyRequestDto, PrincipalDetails userDetails){


        //세션 유저
        Member user = userDetails.getUser();

        // 게시물
        Board board = boardRepository.findById(id).orElseThrow(() -> {

            return new IllegalArgumentException("해당 번호의 게시물이 없스빈다");
        });

        // dto -> entity
        Reply reply = replyRequestDto.toEntity(board, user);


        // 댓글 등록
        Reply save = replyRepository.save(reply);

        // 등록된 댓글 -> dto
        ReplyResponseDto replyResponseDto = new ReplyResponseDto(save);


        return replyResponseDto;
    }








    // 해당 게시물 댓글 조회
    @Transactional
    public List<ReplyResponseDto> findByBoard(Long id) {


        List<Reply> byBoard = replyRepository.findByBoardId(id);


        List<ReplyResponseDto> ReplyListDto =
                byBoard.stream().map(ReplyResponseDto::new)
                        .collect(Collectors.toList());


        for (ReplyResponseDto replyResponseDto : ReplyListDto) {
            System.out.println("replyResponseDto = " + replyResponseDto);
        }


        return ReplyListDto;

    }

    /**
     *
     * 1.넘어온 id를 객체(reply)로 영속화한 후 삭제                           v
     * 2. replyDeleteDto를 만들어서 객체로 매핑하고 영속화 한 후 삭제           v
     * 3. queryDsl 이용해서 삭제
     *
     * 4. 세션검사 ( 같은 아이디 만 삭제가능)                                 v
     *
     */
    
    // 댓글 삭제
    @Transactional
    public void deleteReply(Long id) {

        Reply reply = replyRepository.findById(id).orElseThrow(() -> {

            return new IllegalArgumentException(" 댓글이 없슈");
        });


        replyRepository.delete(reply);

    }


    // 게시물 삭제 -> 댓글 삭제
    @Transactional
    public void deleteReplies(Long boardId) {

        List<Reply> replies = replyRepository.findByBoardId(boardId);

        for (Reply reply : replies) {
            System.out.println("reply = " + reply);

            if (reply != null)
                replyRepository.delete(reply);

        }


    }
    
}
