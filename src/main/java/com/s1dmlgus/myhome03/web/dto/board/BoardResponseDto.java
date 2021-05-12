package com.s1dmlgus.myhome03.web.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import com.s1dmlgus.myhome03.domain.board.Board;
import com.s1dmlgus.myhome03.domain.boardImage.BoardImage;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.web.dto.upload.UploadResultDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class BoardResponseDto {


    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime boardDate;
    private Long MemberId;
    private String MemberName;

    private List<UploadResultDto> uploadResultDtoList;



//    @QueryProjection
//    public BoardResponseDto(Board board) {
//        this.id = board.getId();
//        this.title = board.getTitle();
//        this.content = board.getContent();
//    }


    @Builder
    public BoardResponseDto(Board board, List<BoardImage> boardImages) {
        this.boardId = board.getId();
        this.boardTitle = board.getTitle();
        this.boardContent = board.getContent();
        this.MemberId = board.getMember().getId();
        this.MemberName = board.getMember().getUsername();

        uploadResultDtoList = boardImages.stream().map(boardImage -> {

            UploadResultDto uploadResultDto = UploadResultDto.builder()
                    .fileName(boardImage.getImgName())
                    .uuid(boardImage.getUuid())
                    .build();

            return uploadResultDto;

        }).collect(Collectors.toList());


    }


    @QueryProjection
    public BoardResponseDto(Long boardId, String boardTitle, String boardContent,LocalDateTime boardDate, Long memberId, String memberName) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardDate = boardDate;
        MemberId = memberId;
        MemberName = memberName;
    }
}
