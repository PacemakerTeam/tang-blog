package com.devTest.Firstblog.service;

import com.devTest.Firstblog.model.User;
import com.devTest.Firstblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoCㅎ준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public int joinProc(User user){
        try{
            userRepository.save(user);
            return 1;
        }catch(Exception  e){
            e.printStackTrace();
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        return -1;
    }
}
