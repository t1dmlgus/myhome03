package com.s1dmlgus.myhome03.web.dto.reply;


import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.reply.Reply;
import com.s1dmlgus.myhome03.domain.user.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class ReplyRequestDto {


    @NotBlank(message = "댓글이 작성되지 않았습니다.")
    @Size(max = 20, message = "댓글은 20자 이하만 작성 가능합니드")
    private String content;


    public Reply toEntity(Board board, Member member) {


        return Reply.builder()
                .content(content)
                .board(board)
                .member(member)
                .build();
    }



}
