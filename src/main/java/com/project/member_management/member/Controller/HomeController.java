package com.project.member_management.member.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// 웹이나 앱 같은 클라이언트의 요청을 받아서 처리하는 기능 (view 반환)
// Bean으로 등록하기 위해 annotation 작성
public class HomeController {
    // 기본페이지 요청 메서드
    @GetMapping("/")
    public String index(){
        return "index"; // templates 폴더의 index.html을 찾아감.
    }
}
