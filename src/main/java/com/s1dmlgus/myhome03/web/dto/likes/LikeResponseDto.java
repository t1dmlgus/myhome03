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
    private int status = 0;
    private Long count;
    private Long memberId;




    @Builder
    public LikeResponseDto(Likes likes) {           // ?
        this.likeId = likes.getId();



    }


}
