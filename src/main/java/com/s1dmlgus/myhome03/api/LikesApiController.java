package com.s1dmlgus.myhome03.api;


import com.s1dmlgus.myhome03.service.LikesService;
import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import com.s1dmlgus.myhome03.web.dto.likes.LikeResponseDto;
import com.s1dmlgus.myhome03.web.dto.likes.LikesRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
public class LikesApiController {


    private final LikesService likesService;


    // 좋아요 등록
    @PostMapping("/api/v1/likes")
    public ResponseDto<LikeResponseDto>save(@RequestBody LikesRequestDto likesRequestDto) {



        log.info("likesRequestDto : "+likesRequestDto);

        LikeResponseDto likeResponseDto = likesService.saveLikes(likesRequestDto);

        System.out.println("likeResponseDto = " + likeResponseDto);
        System.out.println("likeCount = " + likeResponseDto.getCount());



        return new ResponseDto<>(HttpStatus.OK.value(), likeResponseDto);
    }


    // 좋아요 취소
    @DeleteMapping("/api/v1/likes")
    public ResponseDto<Long> delete(@RequestBody LikesRequestDto likesRequestDto){

//        System.out.println("getLikeId() = " + likesRequestDto.getLikeId());
//        System.out.println("likesRequestDto.getUserId() = " + likesRequestDto.getUserId());
//        System.out.println("likesRequestDto.getBoardId() = " + likesRequestDto.getBoardId());

        Long likeCount = likesService.deleteLikes(likesRequestDto.getLikeId(), likesRequestDto.getBoardId());

        System.out.println("likeCount = " + likeCount);

        return new ResponseDto<>(HttpStatus.OK.value(), likeCount);
    }

}
