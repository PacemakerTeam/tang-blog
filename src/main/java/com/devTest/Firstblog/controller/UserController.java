package com.devTest.Firstblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
        public String longinForm(){
        return "user/loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("principal");
        return "/user/loginForm";
    }
}
