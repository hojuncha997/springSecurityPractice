//1. ClubAuthMemberDTO.java
//DB를 이용한 로그인 시 사용

package com.example.club.security.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User {

    private String email;

    private String name;

    private boolean fromSocial;

    public ClubAuthMemberDTO(
            String username,
            String password,
            boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {

        //부모 클래스인 User의 생성자를 호출하는 코드
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;
    }
}
