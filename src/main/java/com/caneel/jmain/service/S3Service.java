package com.caneel.jmain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;

    public S3Service(S3Client s3Client){
        this.s3Client = s3Client;
    }

    @Value("${aws.s3.bucket_name}")
    private String bucketName;

    public String uploadFile(File media)
    {
        String key = media.getName();

        String id = UUID.randomUUID().toString();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("media/"+id+"/"+key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        PutObjectResponse response = s3Client.putObject(request, Path.of(media.getAbsolutePath()));

        return id;
    }
}
