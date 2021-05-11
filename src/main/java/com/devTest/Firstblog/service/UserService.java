package com.devTest.Firstblog.service;

import com.devTest.Firstblog.model.RoleType;
import com.devTest.Firstblog.model.User;
import com.devTest.Firstblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoC해준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Transactional //여러 가지 프로세스가 모여서 하나의 서비스가 될 수 있다. 이 전체가 성공해야만 커밋해야한다.
                    //중간에 몇 개만 성공하고 나서 db에 커밋되면 안되잖아. 그래서 @Transactional을 붙여줘서 사용할 수 있다.
    public int joinProc(User user){
        try{
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword) ;//해쉬
            user.setPassword(encPassword);
            user.setRole(RoleType.USER);

            userRepository.save(user);
//            userRepository.save(user);
//            userRepository.save(user);
            return 1;
        }catch(Exception  e){
            e.printStackTrace();
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        return -1;
    }

    @Transactional
    public void updateUser(User user){
        //수정 시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고,
        //영속화 된 User 오브젝트를 수정
        //select를 해서 User오브젝트를 DB로부터 가져오는 이유는 영속화를 위해서
        //영속화 된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
        User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패");
        });
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistence.setPassword(encPassword);
        System.out.println(user.getEmail());
        persistence.setEmail(user.getEmail());
        //회원정보 함수 종료시 = 서비스 종료 =트랜잭션 종료 = flush = commit이 자동으로 된다.
        //영속화된 persistance 객체의 변화가 감지되면(더티 체킹) flush. update문 자동으로 날려줌
    }

}
