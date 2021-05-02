package com.devTest.Firstblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @GetMapping("/user/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/user/loginForm")
        public String longinForm(){
        return "user/loginForm";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session){
        session.removeAttribute("principal");
        return "/user/loginForm";
    }
}
