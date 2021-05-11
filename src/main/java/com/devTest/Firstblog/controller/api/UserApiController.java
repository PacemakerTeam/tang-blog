package com.devTest.Firstblog.controller.api;

import com.devTest.Firstblog.dto.ResponseDto;
import com.devTest.Firstblog.model.RoleType;
import com.devTest.Firstblog.model.User;
import com.devTest.Firstblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Resource //@Service 어노테이션 달령있어서 컴포넌트 스캔의 범위에 들어감.
    //@Resource는 @Autowired랑 같은 기능을 함. 자바표준 <-> 스프링
    private UserService userService;


//@Autowired
//    private HttpSession session 이 말은 스프링 컨테이너가 빈으로 관리한다는 말!

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user){ //Username, password, email.
        System.out.println("UserApiController.save");
        int result = userService.joinProc(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
    }
    //security 모든 페이지 접근 막는다.
    //처음 아이디 user
    //비밀번호 콘솔에 찍힘 1efa9507-7abf-440c-a40a-ba368153f225
    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user){ //key=value, x-www-form-urlencoded
        userService.updateUser(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
