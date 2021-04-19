package com.devTest.Firstblog.test;

import com.devTest.Firstblog.model.RoleType;
import com.devTest.Firstblog.model.User;
import com.devTest.Firstblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

//html파일이 아니라 data를 return해주는 어노테이션 @RestController
@RestController
public class DummyControllerTest {

    @Autowired //의존성 주입 DI
    private UserRepository userRepository;

    //http:localhost:8000/blog/dummy/user
    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    //한 페이지당 2건에 데이터를 리넡받아 볼 예정
    @GetMapping("dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction= Sort.Direction.DESC) Pageable pageable){

//        List<User> users = userRepository.findAll(pageable).getContent();
        Page<User> pagingUser = userRepository.findAll(pageable);
//        if(pagingUser.isLast()){};
//        if(pagingUser.isFirst()){};
        List<User> users = pagingUser.getContent();
        return users;
    }

    //{id}주소로 파라미터를ㅈ ㅓㄴ달받을 수 있음.
    //http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        // user/4을 찾으면 내가 DB에서 못 찾아오게 되면 user가 null이 되잖아.
        //그럼 프로그램에 문제가 생기지 않겠니?
        //Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 사용해
//    User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//        @Override
//        public User get() {
//            return new User();
//        }
//    });
        /*
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>(){

            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다"+ id);
                //IllegalArgumentExccetpion 발생하는 거 AOP로 가로채는 방식으로 에러 처리
            }
        });
        */

        //람다식 (개좋잖아!? 근데 어떻게 줄였는지 정확히 모르겠네.. 람다식 복습!)
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 유저는 없습니다" + id);
        });
        // 요청 : 웹 브라우저
        // user 객체 = 자바 오브젝트
        // 변환 ( 웹브라우저가 이해할 수 있는 데이터 ) -> json (예전 Gson 라이브러리)
        // 스프링부트 = MessageConverter.
        // 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson라이브러리 호출해서
        // user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
        return user;
    }


    //http://localhost:8000/blog/dummy/join(요청)
    //http의 body에 username, password, email 데이터를 가지고 (요청)
    /*
    @PostMapping("/dummy/join")
    public String join(String username, String password, String email){ // key=value(약속된 규칙))
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("email = " + email);
        return "회원가입이 완료되었습니다";
    }
     */
    @PostMapping("/dummy/join")
    public String join(User user){
        System.out.println("user = " + user);
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다";
    }
}
