package com.devTest.Firstblog.controller;

import com.devTest.Firstblog.config.auth.PrincipalDetail;
import com.devTest.Firstblog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"","/"})
//    public String index(@AuthenticationPrincipal PrincipalDetail principal){
    // /WEB-INF/views/index.jsp
//        System.out.println("로그인 사용자 아이디 : " + principal.getUsername() + " | " + principal);
    public String index(Model model){
        model.addAttribute("boards",boardService.boardList());
        return "index"; //@Controller이기 때문에 viewResolver 작동 -> 해당 인덱스 페이지를 넘겨주는데 model(컬렉션)AndView을 가지고 있음.
    }

    //User 권한이 필요
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }
}
