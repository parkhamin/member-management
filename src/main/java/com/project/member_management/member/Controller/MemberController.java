package com.project.member_management.member.Controller;

import com.project.member_management.member.dto.MemberDTO;
import com.project.member_management.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 의존관계 주입
    private final MemberService memberService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm(){
        return "save"; // templates 폴더의 save.html 파일을 찾아감
    }

    // @RequestParam 어노테이션으로도 가능 : uri를 통해 전달된 값을 파라미터로 받아오는 역할(복잡, 번잡)
    // 매개변수를 dto로 해두면 form의 데이터들을 dto에 넣어줌.
    // 즉, html의 데이터를 dto 객체에 담아줌.
    // html -> dto -> service -> repository -> db
    // 회원가입 기능 구현
    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "login";
    }

    // 로그인 페이지 출력 요청
    @GetMapping("/member/login")
    public String loginForm(){
        return "login";
    }

    // 로그인 기능 구현
    @PostMapping("/member/login")
    public String login(HttpSession session, @ModelAttribute MemberDTO memberDTO){
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null){ // db에서 찾은 결과가 있는 경우
            session.setAttribute("loginEmail", loginResult.getMemberEmail()); // 세션에 등록
            return "main";
        } else { // 결과가 없는 경우
            return null;
        }
    }

    // model : 데이터를 템플릿 엔진으로 가지고 가서 화면에 뿌려줄 수 있는 객체
    // 회원 목록 출력 요청
    @GetMapping("/member/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();  // db에서 모든 회원들을 가져와서 리스트에 담고
        model.addAttribute("memberList",  memberDTOList); // model로 화면에 뿌려줌
        return "list";
    }

    // 회원 상세조회 출력 요청
    @GetMapping("/member/{id}")
    public String findById(@PathVariable ("id") Long id, Model model){
        MemberDTO memberDTO = memberService.findByID(id);
        model.addAttribute("member",memberDTO);
        return "detail";
    }

    // 회원 삭제 기능 구현
    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable ("id") Long id){
        memberService.deleteById(id);
        return "redirect:/member/";
    }

    // 내 정보 수정하기 화면 출력 요청
    @GetMapping("/member/update")
    public String updateForm(Model model, HttpSession session){
        String myEmail = (String)session.getAttribute("loginEmail"); // 로그인한 세션을 가져옴
        MemberDTO memberDTO = memberService.updateForm(myEmail); // db에서 로그인한 세션을 조회해서 dto 객체로 변환해서 가져옴
        model.addAttribute("updateMember", memberDTO); // 내 정보를 수정하기 위한 폼에 정보들을 담아줌
        return "update";
    }

    // 내 정보 수정하기 기능 구현
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }

    // 로그아웃 기능 구현
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }

}
