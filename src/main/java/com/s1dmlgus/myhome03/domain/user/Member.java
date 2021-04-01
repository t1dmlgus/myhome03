package com.s1dmlgus.myhome03.domain.user;

import com.s1dmlgus.myhome03.domain.BaseTimeEntity;
import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.like.Likes;
import com.s1dmlgus.myhome03.domain.reply.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@ToString(of = {"id", "username", "password", "role", "age", "email"})
@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {


    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
    private int age;

    private String provider;
    private String providerId;





    @OneToMany(mappedBy = "member")
    List<Board> boards = new ArrayList<>();


    @OneToMany(mappedBy = "member")
    List<Reply> replies = new ArrayList<>();


    @OneToMany(mappedBy = "member")
    List<Likes> likes = new ArrayList<>();


    @Builder

    public Member(String username, String password, String email, Role role, int age, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.age = age;
        this.provider = provider;
        this.providerId = providerId;
        //this.boards = boards;
    }

    // 비밀번호 암호화
    public void encodePassword(String encPassword) {
        this.password = encPassword;
    }

    // 기본 권한설정
    public void setRole(Role role) {
        this.role = role;
    }

    // 기본 provider 설정
    public void setProvider(String provider) {
        this.provider = provider;
    }

}
