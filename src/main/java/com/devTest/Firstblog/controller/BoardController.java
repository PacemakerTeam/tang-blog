package com.devTest.Firstblog.controller;

import com.devTest.Firstblog.config.auth.PrincipalDetail;
import com.devTest.Firstblog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"","/"})
//    public String index(@AuthenticationPrincipal PrincipalDetail principal){
    // /WEB-INF/views/index.jsp
//        System.out.println("로그인 사용자 아이디 : " + principal.getUsername() + " | " + principal);
    public String index(Model model, @PageableDefault(size=2, sort="id", direction= Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("boards", boardService.boardList(pageable));

        return "index"; //@Controller이기 때문에 viewResolver 작동 -> 해당 인덱스 페이지를 넘겨주는데 model(컬렉션)AndView을 가지고 있음.
    }

    @Transactional
    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model){
        //파라미터 받을 땐 @PathVariable int id 넘겨받을 때도 @GetMapping("/board/{id}")
        model.addAttribute("board",boardService.boardDetail(id));
        return "board/detail";
    }

    //User 권한이 필요
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }
}
