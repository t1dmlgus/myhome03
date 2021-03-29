package com.s1dmlgus.myhome03.api;

import com.s1dmlgus.myhome03.web.dto.upload.UploadResultDto;
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
import java.util.UUID;


@Log4j2
@RestController
public class uploadApiController {


    @Value("${com.s1dmlgus.upload.path}")
    private String uploadPath;


    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDto>> uploadFile(MultipartFile[] uploadFiles){


        System.out.println("uploadFiles = " + uploadFiles);


        List<UploadResultDto> resultDtos = new ArrayList<>();       // 파일(이미지) 리스트

        for (MultipartFile uploadFile : uploadFiles) {

            System.out.println("uploadFile = " + uploadFile.getContentType());
            System.out.println("uploadFile = " + uploadFile.getOriginalFilename());
            System.out.println("uploadFile = " + uploadFile.getName());

            if (uploadFile.getContentType().startsWith("image") == false){

                log.warn("이 파일은 이미지 파일이 아닙다ㅣ");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            // 실제 파일 이름 IE나 Edge는 전체 경로로 들어오므로
            String originalName = uploadFile.getOriginalFilename();
            System.out.println("originalName = " + originalName);
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);   // 이해 ok


            log.info("fileName" + fileName);

            // 날짜 폴더 생성
            String folderPath = makeFolder();

            // UUID
            String uuid = UUID.randomUUID().toString();


            // 저장할 파일 이름 중간에 "_" 를 이용해서 구분
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            System.out.println("saveName = " + saveName);

            Path savePath = Paths.get(saveName);                // path 생성

            System.out.println("savePath.toFile() = " + savePath.toFile());
            System.out.println("savePath.getParent() = " + savePath.getParent());

            try{

                // 실제 이미지 저장, 파일 저장
                uploadFile.transferTo(savePath);

                // 섬네일 생성   -> 파일이름 ( s_ 로 시작)
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator
                        + "s_" + uuid + "_" + fileName;

                // 섬네일 파일 생성
                File thumbnailFile = new File(thumbnailSaveName);

                System.out.println("thumbnailFile = " + thumbnailFile);
                // 섬네일 라이브러리 이용
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 130);

                log.info("fileName : " + fileName);
                log.info("uuid : " + uuid);
                log.info("folderPath : " + folderPath);

                resultDtos.add(new UploadResultDto(fileName, uuid, folderPath));

                for (UploadResultDto resultDto : resultDtos) {
                    log.info("resultDtos"+resultDtos);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        System.out.println("resultDtos~~~~~~~~~~~ = " + resultDtos);

        /**
         *
         *  1. 업로드된 확장자가 이미지만 가능하도록 검사
         *  2. 동일한 이름의 파일이 업로드 된다면 기존 파일을 덮어쓰는 문제
         *  3. 업로드된 파일을 저장하는 폴더의 용량
         *
         */

        return new ResponseEntity<>(resultDtos, HttpStatus.OK);

    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName){


        System.out.println("fileName|| display~~ = " + fileName);

        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");

            log.info("fileName : " + srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);        // 파일 생성

            log.info("file : " + file);

            HttpHeaders header = new HttpHeaders();


            // MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));


            log.info("header : " + header);


            // 파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),
                    header, HttpStatus.OK);
//            result = new ResponseEntity<>(header, HttpStatus.OK);



              log.info("result : " +result);


        } catch (Exception e){
            log.error(e.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }


    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){


        System.out.println("fileName = " + fileName);

        String srcFileName = null;


        try{

            srcFileName = URLDecoder.decode(fileName, "UTF-8");

            File file = new File(uploadPath + File.separator + srcFileName);

            boolean result = file.delete();


            File thumbnail = new File(file.getParent(), "s_" + file.getName());
            result = thumbnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        System.out.println("str = " + str);

        String folderPath = str.replace("//", File.separator);

        System.out.println("folderPath = " + folderPath);

        //make folder -------------

        File uploadPathFolder = new File(uploadPath, folderPath);

        if (uploadPathFolder.exists() == false) {

            uploadPathFolder.mkdir();
        }
        return folderPath;

    }
}
