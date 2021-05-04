package com.devTest.Firstblog.service;

import com.devTest.Firstblog.model.User;
import com.devTest.Firstblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoC해준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional //여러 가지 프로세스가 모여서 하나의 서비스가 될 수 있다. 이 전체가 성공해야만 커밋해야한다.
                    //중간에 몇 개만 성공하고 나서 db에 커밋되면 안되잖아. 그래서 @Transactional을 붙여줘서 사용할 수 있다.
    public int joinProc(User user){
        try{
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

}
