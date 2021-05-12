package com.s1dmlgus.myhome03.web.dto.member;

import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.domain.user.Role;
import lombok.Getter;

import java.io.Serializable;



@Getter
public class SessionMember implements Serializable {

    private static final long serialVersionUID = 7354597494288457838L;

    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private int age;


    public SessionMember(Member userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.email = userEntity.getEmail();
        this.role = userEntity.getRole();
        this.age = userEntity.getAge();
    }


}
