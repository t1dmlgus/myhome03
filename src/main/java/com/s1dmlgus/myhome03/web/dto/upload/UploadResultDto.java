package com.s1dmlgus.myhome03.web.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@AllArgsConstructor
@Data
public class UploadResultDto implements Serializable {

    private String fileName;
    private String uuid;
    private String folderPath;


    public String getImageURL(){

        try{
            return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }


    public String getThumbnailURL(){

        try{

            return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


}
