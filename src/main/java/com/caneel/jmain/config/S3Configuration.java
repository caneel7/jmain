package com.caneel.jmain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Configuration {

    @Value("${aws.s3.access_key}")
    private String accessKey;

    @Value("${aws.s3.secret_key}")
    private String secret_key;

    @Value("${aws.s3.region}")
    private String region;


    @Bean
    public S3Client s3Client()
    {
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey,secret_key)
                )).region(Region.of(region))
                .build();
    }

}
