package com.devTest.Firstblog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.security.Principal;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static폴더 이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
        public String longinForm(){
        return "user/loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("principal");
        return "/user/loginForm";
    }
    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code){ //ResponseBody : data를 리턴해주는 컨트롤러 함수
        //이 코드값을 통해 Access Token을 부여받아보자. 지금은 인증코드만 받았다!
        //인증 코드 받은 뒤, 인증 코드로 사용자 토큰을 발급 받는 API이다.
        //인증 코드 받기만으로는 카카오 로그인이 완료되지 앟으며, 사용자 토큰 받기까지 마쳐야 카카오 로그인을 정상적으로 완료할 수 있습니다.
        //필수 값을 받아서 POST요청하시면 됩니다!

        //토큰 발급 요청 주소(POST방식 요청)
        //https://kauth.kakao.com/oauth/toeken
        // 마입타입... key=value로 보내라는 말임
        // Content-type: application/x-www-form-urlencoded;charset=utf-8
        //Parameter Http body에 담을 애들
        //grant_type=authorization_code
        //client_id="REST IP"
        //redirect_url=리다이렉트 된 url
        //code=인증코드
        //client_secret
        //POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
        RestTemplate rt = new RestTemplate();
        //이거 POST방식으로 요청 아주 간편하게 할 수 있도록 도와주는 라이브러리!!!
        //외에도 Retrofit2, OkHttp, RestTemplate 등이 있음.
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id","8a33a6a0caeba8c98df9077858dee986");
        params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
        params.add("code",code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
                new HttpEntity<>(params,headers);

        //Http 쵸어하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
          "https://kauth.kakao.com/oauth/token",
          HttpMethod.POST,
          kakaoTokenRequest,
          String.class
        );
        return "카카오 인증 완료 : 코드 값 " + code+ "<br/>토큰요청에 대한 응답 :" + response;
    }

    @GetMapping("/user/updateForm")
    public String updateForm(@AuthenticationPrincipal Principal principal){
        return "user/updateForm";
    }
}
