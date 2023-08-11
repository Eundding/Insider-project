package com.example.umc_insider.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;



@Service
public class S3Service {
    @Bean
    public AmazonS3 initializeAmazonS3() {
        return null;
    }
    private final String bucketName = "umcinsider";
    private final AmazonS3 s3Client;

    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFileToS3(MultipartFile file) {
        String keyName = "images/" + file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();
            s3Client.putObject(new PutObjectRequest(bucketName, keyName, new ByteArrayInputStream(bytes), new ObjectMetadata()));
            return s3Client.getUrl(bucketName, keyName).toString();
        } catch (IOException e) {
            throw new RuntimeException("File loading failed.", e);
        }
    }

    public String getURLFromS3(String imageName) {
        String keyName = "images/" + imageName;
        return s3Client.getUrl(bucketName, keyName).toString();
    }
}