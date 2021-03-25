package com.s1dmlgus.myhome03.web.dto.reply;


import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.reply.Reply;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OrderBy;

@NoArgsConstructor
@Data
public class ReplyResponseDto {

    private Long replyId;
    private String replyContent;
    private String memberName;



    @Builder
    public ReplyResponseDto(Reply reply) {

        this.replyId = reply.getId();
        this.replyContent = reply.getContent();
        this.memberName = reply.getMember().getUsername();

    }

}
