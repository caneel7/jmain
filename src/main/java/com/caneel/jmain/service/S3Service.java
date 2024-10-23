package com.caneel.jmain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.nio.file.Path;

@Service
public class S3Service {

    private final S3Client s3Client;

    public S3Service(S3Client s3Client){
        this.s3Client = s3Client;
    }

    @Value("${bucket_name}")
    private String bucketName;

    public String uploadFile(File media)
    {
        String key = media.getAbsolutePath();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        PutObjectResponse response = s3Client.putObject(request, Path.of(media.getAbsolutePath()));

        return response.eTag();
    }
}
