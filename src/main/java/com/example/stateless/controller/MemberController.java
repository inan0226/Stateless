package com.example.stateless.controller;

import com.example.stateless.dto.MemberRequest;
import com.example.stateless.entity.Member;
import com.example.stateless.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor // Service를 주입받기 위해 반드시 필요
public class MemberController {

    // Service 의존성 주입
    private final MemberService memberService;

    // 팀원 정보 저장 API
    @PostMapping
    public String saveMember(@RequestBody MemberRequest request) {
        // Service의 저장 로직 호출
        Long savedId = memberService.saveMember(request);
        return savedId + "번 팀원 저장 성공!";
    }

    // 팀원 정보 조회 API
    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        // Service의 조회 로직을 호출하여 그대로 반환
        // (객체를 반환하면 Spring Boot가 자동으로 JSON 형태로 변환해 줍니다)
        return memberService.getMember(id);
    }
}