//2. ClubUserDetailService.java
//ClubAuthMemberDTO.java,JPA를 이용해 DB에서 사용자 정보 가져오기
package com.example.club.security.service;

import com.example.club.entity.ClubMember;
import com.example.club.repository.ClubMemberRepository;
import com.example.club.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service //자동으로 스프링에 빈 등록 -> 자동으로 UserDetailService로 인식한다. 따라서 임시 configure()를 사용하지 않도록 한다.
@RequiredArgsConstructor
public class ClubUserDetailService implements UserDetailsService {

    //jpa사용을 위해 주입
    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        log.info("ClubUserDetailService loadUserByUsername = " + username);

        Optional<ClubMember> result = clubMemberRepository.findByEmail(username, false);

        if(result.isEmpty()){ //if(result.isPresent()){
            throw new UsernameNotFoundException("Check Email or Social ");
        }

        ClubMember clubMember = result.get();
        log.info("---------------------------------");
        log.info(clubMember);

        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toSet()));

                clubAuthMember.setName(clubMember.getName());
                clubAuthMember.setFromSocial(clubMember.isFromSocial());

                return clubAuthMember;
    }
}

/*
 - ClubMemberRepository 를 주입받는 구조로 변경 후 @RequiredArgsConstructor 처리
 - username이 실제로는 ClubMember에서는 email을 의미하므로 이를 이용해서
    ClubMemberRepository의 findByEmail()을 호출(소셜 여부 false)

 - 사용자가 존재하지 않으면 UsernameNotFoundException처리
 - ClubMember를 UserDetails 타입으로 처리하기 위해 ClubAuthMemberDTO 타입으로 변환
 - ClubMemberRole은 스프링 시큐리티에서 사용하는 SimpleGrantedAuthority로 변환
    이 때 'ROLE_' 접두어 추가해서 사용.



 */