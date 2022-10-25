package com.example.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/member").hasRole("USER");
                //스프링 시큐리티에서 USER는 ROLE_USER라는 인증된 사용자를 상수처럼 칭할 때 사용.
                //예를 들면 로그인 성공하면 ROLE_USER 권한을 갖는다든지
        http.formLogin();
        http.csrf();//.disable();
        http.logout(); //csrf를 사용하면 반드시 POST방식으로 로그아웃. 비활성하면 GET으로도 가능
        //스프링시큐리티는 기본적으로 HttpSession사용.
        //invalidatedHttpSession()과 deleteCookies()를 이용해서 쿠키나 세션 무효화 가능



    }
//@Service //자동으로 스프링에 빈 등록 -> 자동으로 UserDetailService로 인식한다. 따라서 임시 configure()를 사용하지 않도록 한다.
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        //사용자 계정은 user1
//        auth.inMemoryAuthentication().withUser("user1")
//                .password("$2a$10$rAWXDLrGuOHQzDS7gU7OOu6gMxJN5bJX5ySPAKjcc0qv9cS9Mnt1O")
//                .roles("USER");
//    }

}
