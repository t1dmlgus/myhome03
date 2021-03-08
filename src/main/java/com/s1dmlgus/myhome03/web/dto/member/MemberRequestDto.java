package com.s1dmlgus.myhome03.web.dto.member;


import com.s1dmlgus.myhome03.domain.user.Member;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class MemberRequestDto {

    private String username;
    private String password;
    private String email;
    private int age;


    public MemberRequestDto(String username, String password, String email, int age) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    public Member toEntity(){

        return Member.builder()
                .username(username)
                .password(password)
                .age(age)
                .email(email)
                .build();



    }



}
