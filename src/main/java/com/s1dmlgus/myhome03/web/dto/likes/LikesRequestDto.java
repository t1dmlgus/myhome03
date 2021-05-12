package com.s1dmlgus.myhome03.web.dto.likes;


import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.like.Likes;
import com.s1dmlgus.myhome03.domain.user.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LikesRequestDto {

    private Long likeId;              // 좋아요 id
    private Long userId;              // 유저 id
    private Long boardId;             // 게시물 id

    private int status = 0;           // 1: 좋아요, 0: default


    public LikesRequestDto(Long userId, Long boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }



    public Likes toEntity(Member member, Board board){

        return Likes.builder()
                .member(member)
                .board(board)
                .build();


    }
}
