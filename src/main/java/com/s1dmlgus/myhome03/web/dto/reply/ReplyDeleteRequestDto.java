package com.s1dmlgus.myhome03.web.dto.reply;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReplyDeleteRequestDto {

    private Long replyId;
    private String userId;
    private String boardId;
    private String username;

}
