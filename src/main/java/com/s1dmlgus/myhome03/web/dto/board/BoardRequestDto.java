package com.s1dmlgus.myhome03.web.dto.board;

import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.web.dto.boardImage.BoardImageDto;
import com.s1dmlgus.myhome03.web.dto.member.SessionMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardRequestDto {

    @NotNull(message = "게시글 제목이 업습니다.")
    @NotBlank(message = "게시글 제목이 없습니다.")
    @Size(max = 20, message = "게시글 제목은 20자 내 가능합니다.")
    private String title;


    private String content;
    private Member member;

    private List<String> boardImage;


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
