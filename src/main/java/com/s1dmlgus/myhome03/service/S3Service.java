package com.s1dmlgus.myhome03.service;



import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import com.s1dmlgus.myhome03.web.dto.upload.UploadResultDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Log4j2
@Service
public class S3Service {

    public static final String CLOUD_FRONT_DOMAIN_NAME="d1482gbgc9jhx6.cloudfront.net";

    private AmazonS3 s3Client;


    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;


    // 의존성 주입 후 초기화를 수행하는 메서드 -> bean이 한번만 초기화
    @Transactional
    @PostConstruct
    public void setS3Client(){

        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);


        // AmazonS3ClientBuilder를 통해 S3 Client를 가져오는데 -> 자격증명이 필요함
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();

    }


    @Transactional
    public List<UploadResultDto> upload(List<UploadResultDto> resultDtos, MultipartFile[] uploadFiles) throws IOException {

        for (MultipartFile uploadFile : uploadFiles) {
            System.out.println("uploadFile = " + uploadFile.getOriginalFilename());

            // 이미지 유효성
            if (!Objects.requireNonNull(uploadFile.getContentType()).startsWith("image")) {

                log.warn("이 파일은 이미지 파일이 아닙다ㅣ");
                return null;
            }

            // UUID
            String uuid = UUID.randomUUID().toString();
            // fileName
            String fileName = uploadFile.getOriginalFilename();


            // 파일 신규 업로드, 수정 업로드 모두 같은 메소드를 사용한다.
            s3Client.putObject(new PutObjectRequest(bucket, uuid+"_"+fileName, uploadFile.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));        // public read 권한 추가


            resultDtos.add(new UploadResultDto(fileName,uuid,CLOUD_FRONT_DOMAIN_NAME));
        }

        log.info("ud : "+resultDtos);
        return resultDtos;
    }


    // 'x' 눌렀을 때 S3에서 Image 삭제
    @Transactional
    public void deleteS3Image(String fileName) {

        String substring = fileName.substring(fileName.indexOf("net/")+4);

        System.out.println("substring = " + substring);
        boolean isExistObject = s3Client.doesObjectExist(bucket, substring);

        System.out.println("isExistObject = " + isExistObject);
        if (isExistObject) {
            s3Client.deleteObject(bucket, substring);
        }
        
    }
}


