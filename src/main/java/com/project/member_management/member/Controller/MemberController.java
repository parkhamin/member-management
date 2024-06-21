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
    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "login";
    }

    @GetMapping("/member/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/member/login")
    public String login(HttpSession session, @ModelAttribute MemberDTO memberDTO){
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null){
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            return null;
        }
    }

    // model : 데이터를 템플릿 엔진으로 가지고 가서 화면에 뿌려줄 수 있는 객체
    @GetMapping("/member/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList",  memberDTOList);
        return "list";
    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable ("id") Long id, Model model){
        MemberDTO memberDTO = memberService.findByID(id);
        model.addAttribute("member",memberDTO);
        return "detail";
    }

    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable ("id") Long id){
        memberService.deleteById(id);
        return "redirect:/member/";
    }

}
