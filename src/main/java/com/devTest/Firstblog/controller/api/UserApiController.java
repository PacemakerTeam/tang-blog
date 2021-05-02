package com.devTest.Firstblog.controller.api;

import com.devTest.Firstblog.dto.ResponseDto;
import com.devTest.Firstblog.model.RoleType;
import com.devTest.Firstblog.model.User;
import com.devTest.Firstblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserApiController {

    @Resource //@Service 어노테이션 달령있어서 컴포넌트 스캔의 범위에 들어감.
    //@Resource는 @Autowired랑 같은 기능을 함. 자바표준 <-> 스프링
    private UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user){ //Username, password, email.
        System.out.println("UserApiController.save");
        //DB에 insert하고 아래에서 retunr하면 된다.
        user.setRole(RoleType.USER);

        int result = userService.joinProc(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
    }

    @PostMapping("/blog/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user){
        System.out.println("UserApiController.login");
        User principal = userService.loginProc(user); //principal (접근주체)
        if(principal !=null ){

        }
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}