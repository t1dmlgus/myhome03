package com.s1dmlgus.myhome03.web.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@NoArgsConstructor
@Data
public class UploadResultDto implements Serializable {

    private String fileName;
    private String uuid;


    private String CLOUD_FRONT_DOMAIN_NAME;


    public String getImageURL(){

        return "https://"+CLOUD_FRONT_DOMAIN_NAME + "/" + uuid+"_" +fileName;
    }


    public UploadResultDto(String fileName, String uuid, String CLOUD_FRONT_DOMAIN_NAME)
    {
        this.fileName = fileName;
        this.uuid = uuid;
        this.CLOUD_FRONT_DOMAIN_NAME = CLOUD_FRONT_DOMAIN_NAME;
    }
}
