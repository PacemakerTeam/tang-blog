package com.devTest.Firstblog.config.auth;

import com.devTest.Firstblog.model.User;
import com.devTest.Firstblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Bean 등록
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
    // password 부분 처리는 알아서 함.
    // username이 DB에 있는지 확인해주면 됨.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username)
                .orElseThrow(()->{ //Optional 타입이기 때문에 .orElseThrow를 씀.. Optional을 배워야겠다.
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
                });
        return new PrincipalDetail(principal); //시큐리티의 세션에 유저 정보가 저장
        //이 때 저장되는 타입이 UserDetails 타입인 것이지!
        //오버라이드 과정이 없으면 user, 콘솔창 passward만 시큐리티 세션에 담긴다.
    }
}
