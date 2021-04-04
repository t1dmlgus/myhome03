package com.s1dmlgus.myhome03.domain.like;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s1dmlgus.myhome03.web.dto.likes.LikeResponseDto;
import com.s1dmlgus.myhome03.web.dto.likes.LikeSearchCondition;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.s1dmlgus.myhome03.domain.like.QLikes.likes;
import static org.springframework.util.StringUtils.hasText;


@Log4j2
public class LikeRepositoryImpl implements LikeRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    public LikeRepositoryImpl(EntityManager em){

        this.queryFactory = new JPAQueryFactory(em);
    }



    @Override
    public Long countLike(Long id) {        // 좋아요 카운트

        long likeCount = queryFactory
                .selectFrom(likes)
                .where(
                        likes.board.id.eq(id)
                )
                .fetchCount();


        return likeCount;
    }

    @Override
    public LikeResponseDto userLike(LikeSearchCondition likeCond) {

        LikeResponseDto likeResponseDto = new LikeResponseDto();            //LikeResponseDto


        if(likeCond.getUserId() != null){                                   // 세션유저가 있는 경우

            likeResponseDto = queryFactory
                    .select(Projections.bean(LikeResponseDto.class,
                            likes.id.as("likeId"), likes.member.id.as("memberId")))
                    .from(likes)
                    .where(
                            boardIdEq(likeCond.getBoardId()),               // boardId
                            userIdEq(likeCond.getUserId())                  // userId
                    )
                    .fetchOne();

            if (likeResponseDto == null) {                                  // 유저가 전에 좋아요를 안눌렀을 경우

                return new LikeResponseDto();
            }
        }

        System.out.println("likeResponseDto = " + likeResponseDto);

        return likeResponseDto;

    }


    private BooleanExpression boardIdEq(Long boardId) {

        System.out.println("boardIdEq = " + boardId);
        return hasText(String.valueOf(boardId)) ? likes.board.id.eq(boardId) : null;
    }

    private BooleanExpression userIdEq(Long userId) {
        System.out.println("userIdEq = " + userId);
        return userId != null ? likes.member.id.eq(userId) : null;
    }


}
