package com.s1dmlgus.myhome03.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

     public Member findByUsername(String username);

     public Boolean existsByUsername(String username);
}
