package com.example.stateless.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    // 사진을 업로드할 때 사용할 클라이언트
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.AP_NORTHEAST_2) // 서울 리전
                .build();
    }

    // 'Presigned URL(7일짜리 임시 링크)'을 만들 때 사용할 객체
    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.AP_NORTHEAST_2)
                .build();
    }
}
