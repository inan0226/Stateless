package com.example.stateless.repository;

import com.example.stateless.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Entity타입, PK타입> 을 상속받으면 기본적인 CRUD 메서드가 자동 생성됩니다!
public interface MemberRepository extends JpaRepository<Member, Long> {
}
