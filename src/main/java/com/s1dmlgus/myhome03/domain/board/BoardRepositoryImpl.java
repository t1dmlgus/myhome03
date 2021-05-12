package com.s1dmlgus.myhome03.domain.board;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.s1dmlgus.myhome03.web.dto.board.BoardResponseDto;
import com.s1dmlgus.myhome03.web.dto.board.BoardSearchCondition;
import com.s1dmlgus.myhome03.web.dto.board.QBoardResponseDto;
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

    // 전체 게시판 페이징

    @Override
    public Page<BoardResponseDto> searchPage(BoardSearchCondition condition, Pageable pageable) {

        QueryResults<BoardResponseDto> results = queryFactory
                .select(new QBoardResponseDto(
                        board.id.as("boardId"),
                        board.title.as("boardTitle"),
                        board.content.as("boardContent"),
                        board.modifiedDate.as("boardDate"),
                        member.id.as("memberId"),
                        member.username.as("memberName")))
                .from(board)
                .leftJoin(board.member, member)
                .where(
                        boardtitleEq(condition.getBoardTitle()),
                        boardcontent(condition.getBoardContent()),
                        usernameEq(condition.getUsername())
                        )
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        // content
        List<BoardResponseDto> content = results.getResults();
        // total
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);

    }

    // new Board
    @Override
    public List<Board> newBoard() {

        List<Board> boards = queryFactory
                .select(board)
                .from(board)
                .orderBy(board.id.desc())
                .limit(9)
                .fetch();

        return boards;
    }



    private BooleanExpression boardtitleEq(String boardTitle) {
        return hasText(boardTitle) ? board.title.contains(boardTitle) : null;

    }

    private BooleanExpression boardcontent(String content) {

        return hasText(content) ? board.content.eq(content) : null;
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? member.username.eq(username) : null;
    }
}
