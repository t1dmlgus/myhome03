package com.s1dmlgus.myhome03.web.dto.board;

import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.user.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoardRequestDto {


    private String title;
    private String content;
    private Member member;


    public BoardRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Board toEntity(Member member){

        return Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();

    }

}
