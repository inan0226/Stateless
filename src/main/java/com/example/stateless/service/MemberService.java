package com.example.stateless.service;

import com.example.stateless.dto.MemberRequest;
import com.example.stateless.entity.Member;
import com.example.stateless.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 팀원 저장 로직
    @Transactional
    public Long saveMember(MemberRequest request) {
        // 전달받은 DTO의 데이터를 꺼내서 Member Entity 객체로 생성
        Member member = new Member(request.getName(), request.getAge(), request.getMbti());

        // Repository를 통해 DB에 저장 (JPA가 INSERT 쿼리를 실행해 줍니다)
        Member savedMember = memberRepository.save(member);

        // 저장 후 자동 생성된 PK(id) 값을 반환
        return savedMember.getId();
    }

    // 팀원 조회 로직
    @Transactional(readOnly = true)
    public Member getMember(Long id) {
        // findById는 값이 있을 수도, 없을 수도 있는 Optional 객체를 반환합니다.
        // orElseThrow를 사용하면 값이 없을 때 깔끔하게 예외를 발생시킬 수 있습니다.
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 팀원을 찾을 수 없습니다. ID: " + id));
    }

    // 프로필 사진 경로 업데이트 로직
    @Transactional
    public void updateProfileImage(Long id, String profileImageKey) {
        // 위에 만들어둔 getMember 메서드를 재사용해서 DB에 있는 해당 멤버를 불러옵니다.
        Member member = getMember(id);

        // Member 엔티티에 만들어둔 메서드를 호출해 사진 경로(S3 Key)를 업데이트합니다.
        member.updateProfileImage(profileImageKey);

        // 변경 감지(Dirty Checking) 덕분에 별도의 save() 호출이 필요 없습니다.
    }
}