package com.s1dmlgus.myhome03.domain.like;

import com.s1dmlgus.myhome03.domain.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface LikeRepository extends JpaRepository<Likes, Long>, LikeRepositoryCustom {


    public List<Likes> findByBoardId(Long id);

    public List<Likes> findByMemberId(Long id);

    void deleteByBoardId(Long boardId);
}
