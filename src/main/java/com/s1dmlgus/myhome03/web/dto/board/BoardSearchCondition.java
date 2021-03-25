package com.s1dmlgus.myhome03.web.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardSearchCondition {
    
    // 게시글 명, 작성자, 콘텐츠

    private String boardTitle;
    private String username;
    private String boardContent;


}
