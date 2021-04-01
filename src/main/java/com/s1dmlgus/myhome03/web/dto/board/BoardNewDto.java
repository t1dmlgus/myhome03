package com.s1dmlgus.myhome03.web.dto.board;

import com.s1dmlgus.myhome03.web.dto.upload.UploadResultDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardNewDto {

    private Long boardId;

    private UploadResultDto uploadResultDto;

}
