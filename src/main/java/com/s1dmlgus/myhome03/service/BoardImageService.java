package com.s1dmlgus.myhome03.service;


import com.s1dmlgus.myhome03.domain.boardImage.BoardImage;
import com.s1dmlgus.myhome03.domain.boardImage.BoardImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class BoardImageService {

    private final BoardImageRepository boardImageRepository;
    private final S3Service s3Service;


    // 이미지 삭제
    @Transactional
    public void deleteBoardImages(Long boardId) {

        List<BoardImage> byBoardId = boardImageRepository.findByBoardId(boardId);
        for (BoardImage boardImage : byBoardId) {

            s3Service.deleteS3Image(boardImage.getImgName());

        }

        boardImageRepository.deleteByBoardId(boardId);
    }
    
    
}
