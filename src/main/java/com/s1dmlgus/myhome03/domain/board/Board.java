package com.s1dmlgus.myhome03.domain.board;

import com.s1dmlgus.myhome03.domain.BaseTimeEntity;
import com.s1dmlgus.myhome03.domain.reply.Reply;
import com.s1dmlgus.myhome03.domain.user.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString(of = {"id","title","content","member"})
@Getter
@NoArgsConstructor
@Entity
public class Board extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;



    @OneToMany(mappedBy = "board")
    List<Reply> replies = new ArrayList<>();

    @Builder
    public Board(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }



    // 업데이트 로직
    public void update(String title, String content){

        this.title = title;
        this.content = content;
    }

}
