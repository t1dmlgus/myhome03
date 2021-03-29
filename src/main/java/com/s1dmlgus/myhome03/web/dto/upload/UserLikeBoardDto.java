package com.s1dmlgus.myhome03.web.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLikeBoardDto {

    private Long boardId;

    private UploadResultDto uploadResultDto;


}
