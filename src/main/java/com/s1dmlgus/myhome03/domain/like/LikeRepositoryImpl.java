package com.s1dmlgus.myhome03.domain.like;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s1dmlgus.myhome03.web.dto.likes.LikeResponseDto;
import com.s1dmlgus.myhome03.web.dto.likes.LikeSearchCondition;

import javax.persistence.EntityManager;

import java.util.List;

import static com.s1dmlgus.myhome03.domain.like.QLikes.likes;
import static org.springframework.util.StringUtils.hasText;

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
    
    @Override                                            // 좋아요 누른 유저확인 있으면 반환 :1 없음 : null
    public LikeResponseDto boardLike(LikeSearchCondition likeCond) {

        LikeResponseDto likeResponseDto = queryFactory
                .select(Projections.fields(LikeResponseDto.class,
                        likes.id.as("likeId"),
                        likes.status
                ))
                .from(likes)
                .where(
                        //likes.board.id.eq(likeCond.getBoardId())
                        boardIdEq(likeCond.getBoardId()),               // and
                        userIdEq(likeCond.getUserId())
                )
                .fetchOne();


//        System.out.println(likes.board.id.eq(likeCond.getBoardId()));
//        System.out.println(userIdEq(likeCond.getUserId()));

        
        return likeResponseDto;
    }

    private BooleanExpression boardIdEq(Long boardId) {

        return hasText(String.valueOf(boardId)) ? likes.board.id.eq(boardId) : null;

    }

    private BooleanExpression userIdEq(Long userId) {


        return hasText(String.valueOf(userId)) ? likes.member.id.eq(userId) : null;

    }


}
