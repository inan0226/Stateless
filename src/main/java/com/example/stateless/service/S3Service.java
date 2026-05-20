package com.example.stateless.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner; // 임시 링크 발급용 객체 추가

    // application.yml에 적어둔 버킷 이름을 가져옵니다.
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    // 생성자에 S3Presigner 주입 추가
    public S3Service(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    // S3에 이미지 업로드하고 저장된 경로(Key) 반환
    public String uploadImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String storeFileName = UUID.randomUUID() + "_" + originalFilename;

        String objectKey = "profiles/" + storeFileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return objectKey;
    }

    // 7일 동안만 유효한 Presigned URL 생성
    public String getPresignedUrl(String objectKey) {
        if (objectKey == null || objectKey.trim().isEmpty()) {
            return null;
        }

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        // 요구사항에 맞춰 7일(7 Days)로 유효기간 설정
        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofDays(7))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest =
                s3Presigner.presignGetObject(getObjectPresignRequest);

        return presignedGetObjectRequest.url().toString();
    }
}