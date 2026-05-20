package com.example.stateless.controller;

import com.example.stateless.dto.MemberRequest;
import com.example.stateless.entity.Member;
import com.example.stateless.service.MemberService;
import com.example.stateless.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor // Service를 주입받기 위해 반드시 필요
public class MemberController {

    // Service 의존성 주입
    private final MemberService memberService;
    private final S3Service s3Service; // S3 서비스 주입 추가

    // 팀원 정보 저장 API
    @PostMapping
    public String saveMember(@RequestBody MemberRequest request) {
        Long savedId = memberService.saveMember(request);
        return savedId + "번 팀원 저장 성공!";
    }

    // 팀원 정보 조회 API
    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.getMember(id);
    }

    //  프로필 사진 업로드 API
    @PostMapping("/{id}/profile-image")
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image) throws IOException {

        // S3에 이미지를 업로드하고 고유 경로(Key)를 받아옴
        String objectKey = s3Service.uploadImage(image);

        // DB에 해당 팀원의 프로필 사진 경로 업데이트
        memberService.updateProfileImage(id, objectKey);

        return ResponseEntity.ok("프로필 사진 업로드 및 DB 저장 성공!");
    }

    //프로필 사진 7일 임시 링크(Presigned URL) 발급 API
    @GetMapping("/{id}/profile-image")
    public ResponseEntity<String> getProfileImageUrl(@PathVariable Long id) {

        // 팀원 정보 조회
        Member member = memberService.getMember(id);

        // 해당 팀원의 프로필 사진 S3 경로(Key) 가져오기
        String objectKey = member.getProfileImageKey();

        // 등록된 사진이 없다면 예외 처리
        if (objectKey == null) {
            return ResponseEntity.ok("등록된 프로필 사진이 없습니다.");
        }

        // 3Service를 통해 7일짜리 접근 가능한 임시 URL 발급
        String presignedUrl = s3Service.getPresignedUrl(objectKey);

        return ResponseEntity.ok(presignedUrl);
    }
}