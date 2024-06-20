package com.project.member_management.member.repository;

import com.project.member_management.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // 이메일로 회원 찾기
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
