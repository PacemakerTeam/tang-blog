package com.devTest.Firstblog.config.auth;

import com.devTest.Firstblog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
public class PrincipalDetail implements UserDetails {
    private User user; //extends해오는 게 상속, 이렇게 품고 있는게 컴포지션

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //계정이 만료되지 않았는지 리턴한다. ( true: 만료안됨 )
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있지 않았는지 리턴한다 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되지 않았는지를 리턴한다(true : 만료안됨 )
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 화성화(사용가능)인지 리턴한다(true : 활성화 )
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정의 권한 목록을 리턴한다 ( 권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 우리는 한 개만!)
    // Collection<? extends GrantedAuthority>
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
//        collectors.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "ROLE_"+user.getRole(); // ROLE_USER
//            }// 스프링에서 ROLE을 받을 때 규칙 ROLE_ prefix로 꼭 넣어줘야 함.
//        });

        //람다식 사용하면 한 줄로 사용가능!
        collectors.add(() -> {return "ROLE_"+user.getRole();});
        return collectors;
    }
}
