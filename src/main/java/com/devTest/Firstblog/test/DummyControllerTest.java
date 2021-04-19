package com.devTest.Firstblog.test;

import com.devTest.Firstblog.model.RoleType;
import com.devTest.Firstblog.model.User;
import com.devTest.Firstblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

//html파일이 아니라 data를 return해주는 어노테이션 @RestController
@RestController
public class DummyControllerTest {

    @Autowired //의존성 주입 DI
    private UserRepository userRepository;



    //save함수는 id를 전달하지 않으면 insert를 해주고,
    //save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    //save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해줘요.
    // email, password 수정해보자.
    //업데이트의 경우 PutMapping을 쓴다.
    @Transactional //시작될 때 Transaction만들어졌다가, 종류 될 때 Transaction.commit이 일어남.
    @PutMapping("/dummy/user/{id}")//@GetMapping("/dummy/user/{id}")같아도 된다. 요청이 다르다.
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){
        //json 데이터를 요청 -> java Object(MessageConverter의 Jackson라이브러리가)로 변환해서 받아줘요)
        System.out.println("id = " + id);
        System.out.println("requestUser = " + requestUser);
        System.out.println("passowrd = " + requestUser.getPassword());
        System.out.println("email = " + requestUser.getEmail());

        // 방법 1
//        requestUser.setId(id);
//        requestUser.setUsername("ssar");
//        userRepository.save(requestUser);
        //save를 통해서 update를 한다? 이건 새로 넣는거나 마찬가지...

        // 방법 2
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

//        userRepository.save(user);
        //그래서 그냥 객체 가져와서 그거 set해서 날림...
        //보기에는 전체를 다시 데이터 넣은 것 처럼 보이지만 아님.
        // update처럼 작동함. 1차 캐시에 User user 스냅샷이 저장되어 있고, 바뀐 것만 update쿼리문 만들어서 날림

        //방법 3  @Transactional
        //save호출 안했는데 ? 된다.
        //userRepository.save(user);

        //더티 체킹
        //스냅샷이랑 변경 된 데이터 감지 -> update flush -> 더티 체킹

        return user;
    }

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
