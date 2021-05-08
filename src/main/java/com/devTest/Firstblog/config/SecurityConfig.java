package com.devTest.Firstblog.config;

import com.devTest.Firstblog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration // 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것. IoC관리
@EnableWebSecurity // 시큐리티 필터 추가 = 스프링 시큐리티가 활성화가 되어 있는데 설정을 지금 파일에서 하겠다. @EnableWebSecurity
                    // => 시큐리티 필터가 등록이 된다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean //IoC가 된다.
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    }

    // 시큐리티가 대신 로그인해주는데 password를 가로채기를 한다.
    // 해당 password가 뭘로 해쉬가 되어 회원가입이 되어있는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬와 비교할 수 있음.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
                .authorizeRequests()
                .antMatchers("/","/auth/**", "/js/**", "/css/**", "/image/**")
                .permitAll()
                .anyRequest()
                .authenticated()
            .and().formLogin()
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc")// 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로챈다. 그리고 대신 로그인해준다.
                .defaultSuccessUrl("/"); //성공하면, 요청이 완료되면 이 페이지로 보내준다.
//                .failureUrl("/fail"); //실패하면, /fail페이지로 보낸다.
    }
}
