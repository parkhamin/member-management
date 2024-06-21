package com.project.member_management.member.service;

import com.project.member_management.member.dto.MemberDTO;
import com.project.member_management.member.entity.MemberEntity;
import com.project.member_management.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        // repository로 데이터를 넘겨주기 위해서는 entity 객체로 넘겨줘야 함
        memberRepository.save(MemberEntity.toMemberEntity(memberDTO));
    }

    public MemberDTO login(MemberDTO memberDTO) {
        // 데이터베이스에서 찾은 로그인 한 entity 객체
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (optionalMemberEntity.isPresent()){
            // 조회 결과가 있다
            MemberEntity memberEntity = optionalMemberEntity.get();
            // 비밀번호가 일치
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                // entity -> dto 변환 후 리턴
                return MemberDTO.toMemberDTO(memberEntity);
            } else { // 비밀번호 불일치
                return null;
            }
        } else { // 데이터베이스에서 로그인하려고 하는 사용자가 없는 경우
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        for(MemberEntity memberEntity: memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }

        return memberDTOList;
    }

    public MemberDTO findByID(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }
}
