package com.s1dmlgus.myhome03.domain.board;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardSearchCondition;
import com.s1dmlgus.myhome03.web.dto.QBoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.s1dmlgus.myhome03.domain.board.QBoard.board;
import static com.s1dmlgus.myhome03.domain.user.QMember.member;
import static org.springframework.util.StringUtils.hasText;

public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);

    }

    @Override
    public Page<BoardResponseDto> searchPage(BoardSearchCondition condition, Pageable pageable) {

        QueryResults<BoardResponseDto> results = queryFactory
                .select(new QBoardResponseDto(
                        board.id.as("boardId"),
                        board.title.as("boardTitle"),
                        board.content.as("boardContent"),
                        member.id.as("memberId"),
                        member.username.as("memberName")))
                .from(board)
                .leftJoin(board.member, member)
                .where(
                        boardtitleEq(condition.getBoardTitle()),
                        usernameEq(condition.getUsername()),
                        boardcontent(condition.getBoardContent())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();



        List<BoardResponseDto> content = results.getResults();
        long total = results.getTotal();


        return new PageImpl<>(content, pageable, total);

    }

    private BooleanExpression boardcontent(String content) {

        return hasText(content) ? board.content.eq(content) : null;
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? member.username.eq(username) : null;
    }

    private BooleanExpression boardtitleEq(String boardTitle) {
        return hasText(boardTitle) ? board.title.eq(boardTitle) : null;

    }
}
