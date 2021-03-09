package com.s1dmlgus.myhome03.web.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.user.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoardResponseDto {


    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private Long MemberId;
    private String MemberName;


//    @QueryProjection
//    public BoardResponseDto(Board board) {
//        this.id = board.getId();
//        this.title = board.getTitle();
//        this.content = board.getContent();
//    }


    @Builder
    public BoardResponseDto(Board board) {
        this.boardId = board.getId();
        this.boardTitle = board.getTitle();
        this.boardContent = board.getContent();
        this.MemberId = board.getMember().getId();
        this.MemberName = board.getMember().getUsername();
    }


    @QueryProjection
    public BoardResponseDto(Long boardId, String boardTitle, String boardContent, Long memberId, String memberName) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        MemberId = memberId;
        MemberName = memberName;
    }
}
