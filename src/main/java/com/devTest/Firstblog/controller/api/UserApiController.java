package com.devTest.Firstblog.controller.api;

import com.devTest.Firstblog.config.auth.PrincipalDetail;
import com.devTest.Firstblog.dto.ResponseDto;
import com.devTest.Firstblog.model.RoleType;
import com.devTest.Firstblog.model.User;
import com.devTest.Firstblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Resource //@Service 어노테이션 달령있어서 컴포넌트 스캔의 범위에 들어감.
    //@Resource는 @Autowired랑 같은 기능을 함. 자바표준 <-> 스프링
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManger;

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
        //여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음
        //하지만 세션값은 변경되지 않은 상태이기 때문에
        //회원정보 변경 -> 했는데 실제로 보면 변경이 안되어있음.. 세션 그대로 연결되어있어서.
        //우리가 직접 세션값을 변경해줄 것!
        /*
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null,principal.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        session.setAttribute("SPRING_SECURITY_CONTEXT",securityContext);
         */
        //세션 등록

        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        Authentication authentication = authenticationManger.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
