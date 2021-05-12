package com.s1dmlgus.myhome03.web.dto.member;


import com.s1dmlgus.myhome03.domain.user.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@NoArgsConstructor
@Data
public class MemberRequestDto {



    // @NotBlack 가 @NotNull 포함
    @NotBlank(message = "유저네임을 입력하세02요")
    @Size(max=20, message = "유저네임 길이를 초과했습니02다")
    private String username;

    //@NotNull(message = "비밀번호 키값이 없습니다.")
    @NotBlank(message = "비밀번호가 없습니다")
    private String password;

    @NotBlank(message = "이메일이 없습니02다")
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
