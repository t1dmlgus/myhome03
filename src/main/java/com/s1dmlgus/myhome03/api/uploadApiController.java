package com.s1dmlgus.myhome03.api;

import com.s1dmlgus.myhome03.service.S3Service;
import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import com.s1dmlgus.myhome03.web.dto.upload.UploadResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Log4j2
@RequiredArgsConstructor
@RestController
public class uploadApiController {

    private final S3Service s3Service;

//    @Value("${com.s1dmlgus.upload.path}")
//    private String uploadPath;


    // 파일 업로드
    @PostMapping("/uploadAjax")
    public ResponseDto<List<UploadResultDto>> uploadFile(MultipartFile[] uploadFiles) throws IOException {

        // 가공된 이미지 정보
        System.out.println("uploadFiles = " + uploadFiles);

        for (MultipartFile uploadFile : uploadFiles) {
            System.out.println("uploadFile.data = " + uploadFile);
        }

        // 파일(이미지) 리스트
        List<UploadResultDto> resultDtos = new ArrayList<>();

        // 파일(이미지) 업로드 -> S3
        s3Service.upload(resultDtos, uploadFiles);


        log.info("resultDtos api conttt222 : "+resultDtos);

        return new ResponseDto<>(HttpStatus.OK.value(), resultDtos);
    }


    // 업로드된 파일 삭제
    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){

        System.out.println("fileName = " + fileName);

        s3Service.deleteS3Image(fileName);


        return new ResponseEntity<>(true, HttpStatus.OK);
    }


}
