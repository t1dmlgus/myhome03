package com.s1dmlgus.myhome03.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data                       // 406 에러
@AllArgsConstructor
public class ResponseDto<T> {

    int status;
    T data;
}
