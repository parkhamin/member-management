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
        List<MemberDTO> memberDTOList = new ArrayList<>(); // 화면에 뿌려주기 위한 dto 객체들을 담을 리스트 생성

        for(MemberEntity memberEntity: memberEntityList){ // db에서 가져온 entity 객체들을 for 반복문을 통해
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity)); // dto 객체로 변환해 리스트에 새로 담아줌
        }

        return memberDTOList;
    }

    public MemberDTO findByID(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){ // id를 통해 db에서 조회한 결과가 있는 경우
            return MemberDTO.toMemberDTO(optionalMemberEntity.get()); // dto로 변환한 후 리턴
        } else { // 특정 id를 가진 회원이 db에 없는 경우
            return null;
        }
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()){ // 특정 이메일을 가진 회원이 db에 있는 경우
            return MemberDTO.toMemberDTO(optionalMemberEntity.get()); // dto로 변환한 후 리턴
        } else { // 특정 이메일을 가진 회원이 db에 없는 경우
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }
}
