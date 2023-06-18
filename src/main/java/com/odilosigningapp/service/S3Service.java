package com.odilosigningapp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    public void uploadFile(MultipartFile file, String key) throws IOException;
    public void deleteFile(String key);
}
