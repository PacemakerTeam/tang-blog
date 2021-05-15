package com.devTest.Firstblog.controller;

import com.devTest.Firstblog.model.KakaoProfile;
import com.devTest.Firstblog.model.OAuthToken;
import com.devTest.Firstblog.model.User;
import com.devTest.Firstblog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.UUID;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static폴더 이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

    @Value("${cos.key}")
    private String cosKey;

    @Autowired
    private AuthenticationManager authenticationManger;

    @Resource
    private UserService userService;

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
    public String kakaoCallback(String code){ //ResponseBody : data를 리턴해주는 컨트롤러 함수
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

        //Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
          "https://kauth.kakao.com/oauth/token",
          HttpMethod.POST,
          kakaoTokenRequest,
          String.class
        );
//        return "카카오 인증 완료 : 코드 값 " + code+ "<br/>토큰요청에 대한 응답 :" + response;
        // Gson, Json Simple, ObjectMapper 등 라이브러리가 있음.
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(),OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(oauthToken.getAccess_token());

        //이 과정은 Service에서 이루어지는 게 좋긴 하다...
        //그냥 한 눈에 보기 좋으라고 컨트롤러에 다 때려 넣었음!

        RestTemplate rt2 = new RestTemplate();
        //이거 POST방식으로 요청 아주 간편하게 할 수 있도록 도와주는 라이브러리!!!
        //외에도 Retrofit2, OkHttp, RestTemplate 등이 있음.
        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization","Bearer "+oauthToken.getAccess_token());
        headers2.add("content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        //Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
        System.out.println(response2);

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(),KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // User 오브젝트 : username, passowrd, email
        System.out.println("카카오 아이디(번호) + " + kakaoProfile.getId());
        System.out.println("카카오 아이디(번호) + " + kakaoProfile.getKakao_account().getEmail());

        System.out.println("블로그서버(카카오) 유저네임 : " + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
        System.out.println("블로그서버(카카오) 이메일 : " + kakaoProfile.getKakao_account().getEmail());
//        UUID garbagePassword = UUID.randomUUID();
        //다음번에 로그인 시도했을 때... 로그인이 안됨.
        //UUID란 중복되지 않는 어떤특정 값을 만들어내는 알고리즘
        System.out.println("블로그서버 패스워드 : " + cosKey);

        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
                .password(cosKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();

        //가입자 혹은 비가입자 체크해서 처리
        User originUser = userService.checkUsername(kakaoUser.getUsername());
        if(originUser.getUsername() == null){
            System.out.println("기존 회원이 아닙니다.............!");
            userService.joinProc(kakaoUser);
        }
        //로그인 처리
        Authentication authentication = authenticationManger.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(@AuthenticationPrincipal Principal principal){
        return "user/updateForm";
    }
}
