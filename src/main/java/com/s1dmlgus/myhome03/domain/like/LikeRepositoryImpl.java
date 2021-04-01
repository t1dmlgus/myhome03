package com.s1dmlgus.myhome03.domain.like;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s1dmlgus.myhome03.web.dto.likes.LikeResponseDto;
import com.s1dmlgus.myhome03.web.dto.likes.LikeSearchCondition;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;

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
    public Long userLike(LikeSearchCondition likeCond) {

        Long memberId = null;
        if(likeCond.getUserId() != null){

            memberId = queryFactory
                    .select(likes.member.id)
                    .from(likes)
                    .where(
                            //likes.board.id.eq(likeCond.getBoardId())
                            boardIdEq(likeCond.getBoardId()),               // 687
                            userIdEq(likeCond.getUserId())                  // userId
                    ).distinct()
                    .fetchOne();
        }


        return memberId;
    }


    private BooleanExpression boardIdEq(Long boardId) {

        return hasText(String.valueOf(boardId)) ? likes.board.id.eq(boardId) : null;
    }

    private BooleanExpression userIdEq(Long userId) {

        return userId != null ? likes.member.id.eq(userId) : null;
    }


}
