package com.keduit.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/*
파일을 처리하는 클래스
 */
@Service
@Log
public class FileService {
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData)
            throws IOException {
        UUID uuid = UUID.randomUUID(); // 고유성을 보장하는 ID를 만들기 위한 표준 규약
        String extention = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extention; // 파일 업로드시 중복이름 방지를 위해, uuid로 파일명 생성
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
