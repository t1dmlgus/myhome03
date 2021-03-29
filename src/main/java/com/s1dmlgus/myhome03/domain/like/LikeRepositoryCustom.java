package com.s1dmlgus.myhome03.domain.like;

import com.querydsl.core.Tuple;
import com.s1dmlgus.myhome03.web.dto.likes.LikeResponseDto;
import com.s1dmlgus.myhome03.web.dto.likes.LikeSearchCondition;

import java.util.List;

public interface LikeRepositoryCustom {


    public Long countLike(Long id);

    public LikeResponseDto boardLike(LikeSearchCondition likeCond);

    public List<Likes> userLike(LikeSearchCondition likeCond);

}
