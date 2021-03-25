package com.s1dmlgus.myhome03.web.dto.likes;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class LikeSearchCondition {

    private Long userId;
    private Long boardId;


    @Builder
    public LikeSearchCondition(Long userId, Long boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }
}
