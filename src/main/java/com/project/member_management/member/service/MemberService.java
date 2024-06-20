package com.project.member_management.member.service;

import com.project.member_management.member.dto.MemberDTO;
import com.project.member_management.member.entity.MemberEntity;
import com.project.member_management.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        // repository로 데이터를 넘겨주기 위해서는 entity 객체로 넘겨줘야 함
        memberRepository.save(MemberEntity.toMemberEntity(memberDTO));
    }
}
