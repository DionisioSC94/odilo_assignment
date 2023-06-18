package com.odilosigningapp.service.implementation;
import com.odilosigningapp.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class S3ServiceImplementation implements S3Service {
    private static final String BUCKET_NAME = "odilo-certificates";

    private static final Region REGION = Region.US_EAST_1;

    public void uploadFile(MultipartFile multipartFile, String key) throws IOException {
        File file = convertMultipartFileToFile(multipartFile);

        S3Client s3Client = generateS3Client();
        PutObjectRequest putObjectRequest = createPutObjectRequest(BUCKET_NAME, key, file);
        PutObjectResponse response = s3Client.putObject(putObjectRequest, file.toPath());

        //detele file from memory
        file.delete();

        //TODO: log not working. This is a prototyped solution.
        //log.info("A file has been uploaded to the S3 bucket");
    }

    public void deleteFile(String fileName) {
        S3Client s3Client = generateS3Client();

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
    private static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile(multipartFile.getOriginalFilename(), null);
        multipartFile.transferTo(file);
        return file;
    }
    private S3Client generateS3Client() {
        return S3Client.builder()
                .region(REGION)
                .credentialsProvider(InstanceProfileCredentialsProvider.builder().build())
                .build();
    }
    private PutObjectRequest createPutObjectRequest(String bucketName, String key, File file) {
        return PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .build();
    }
}
