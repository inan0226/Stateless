package com.example.stateless.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 알아서 ID를 1씩 증가시켜 줍니다.
    private Long id;

    private String name;
    private int age;
    private String mbti;

    // S3에 저장된 사진의 고유 경로를 저장할 필드 추가
    private String profileImageKey;

    // Member 객체를 생성할 때 사용할 생성자
    public Member(String name, int age, String mbti) {
        this.name = name;
        this.age = age;
        this.mbti = mbti;
    }

    // 사진 경로를 업데이트하는 메서드 추가
    public void updateProfileImage(String profileImageKey) {
        this.profileImageKey = profileImageKey;
    }
}