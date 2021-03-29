package com.s1dmlgus.myhome03.domain.boardImage;


import com.s1dmlgus.myhome03.domain.BaseTimeEntity;
import com.s1dmlgus.myhome03.domain.board.Board;
import lombok.*;

import javax.persistence.*;


@ToString(exclude = {"board"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class BoardImage extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="boardImage_id")
    private Long iNum;

    private String uuid;

    private String imgName;

    private String path;


    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

}
