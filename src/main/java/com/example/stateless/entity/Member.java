package com.example.stateless.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor // JPA는 기본 생성자가 필수입니다.
public class Member {

    @Id // 이 필드를 기본키(PK)로 지정합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 알아서 ID를 1씩 증가시켜 줍니다 (Auto Increment).
    private Long id;

    private String name;
    private int age;
    private String mbti;

    // Member 객체를 생성할 때 사용할 생성자
    public Member(String name, int age, String mbti) {
        this.name = name;
        this.age = age;
        this.mbti = mbti;
    }
}