package com.s1dmlgus.myhome03.web.dto.member;

import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.like.Likes;
import com.s1dmlgus.myhome03.domain.reply.Reply;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.domain.user.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class MemberResponseDto {

    private Long memberId;
    private String username;
    private int age;
    private String email;
    private Role role;
    private List<Likes> likes;
    private List<Board> boards;
    private List<Reply> replies;



    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime boardDate;
    private Long MemberId;
    private String MemberName;



    public MemberResponseDto(Member findMember) {

        this.memberId = findMember.getId();
        this.username = findMember.getUsername();
        this.age = findMember.getAge();
        this.email = findMember.getEmail();
        this.role = findMember.getRole();
        this.boards = findMember.getBoards();
        this.replies = findMember.getReplies();
        this.likes = findMember.getLikes();

    }
}
