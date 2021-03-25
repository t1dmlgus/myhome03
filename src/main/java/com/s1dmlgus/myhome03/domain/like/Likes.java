package com.s1dmlgus.myhome03.domain.like;

import com.s1dmlgus.myhome03.domain.BaseTimeEntity;
import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.user.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@ToString(of = {"id", "status"})
@Getter
@NoArgsConstructor
@Entity
public class Likes extends BaseTimeEntity {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int status;      // 1: 좋아요, 0: default


    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;


    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    @Builder
    public Likes(int status, Board board, Member member) {
        this.status = status;
        this.board = board;
        this.member = member;
    }



}
