package com.s1dmlgus.myhome03.web.dto.likes;


import com.querydsl.core.annotations.QueryProjection;
import com.s1dmlgus.myhome03.domain.like.Likes;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LikeResponseDto {

    private Long likeId;
    private int status;
    private Long count;


    @Builder
    public LikeResponseDto(Likes likes) {           // ?
        this.likeId = likes.getId();
        this.status = likes.getStatus();


    }


}
