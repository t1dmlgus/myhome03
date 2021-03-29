package com.s1dmlgus.myhome03.domain.boardImage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {


    List<BoardImage> findByBoardId(Long id);
}
