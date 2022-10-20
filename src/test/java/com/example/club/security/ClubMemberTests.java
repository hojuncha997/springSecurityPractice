package com.example.club.security;

import com.example.club.entity.ClubMember;
import com.example.club.entity.ClubMemberRole;
import com.example.club.repository.ClubMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTests {
    @Autowired
    private ClubMemberRepository repository;//인터페이스

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies(){
        //1-80까지는 USER만
        //81-90 USER, MANAGER
        //91-100 USER, MANAGER, ADMIN

        IntStream.rangeClosed(1,100).forEach(i ->{
            ClubMember clubMember = ClubMember.builder()
                    .email("user" + i + "@testemail.com")
                    .name("사용자" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            //default role
            clubMember.addMemberRole(ClubMemberRole.USER);

            if(i > 80){
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }
            if(i > 90){
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }

            repository.save(clubMember);
        });

    }

    @Test
    public void testRead(){
        Optional<ClubMember> result = repository.findByEmail("user95@testemail.com", false);

        ClubMember clubMember = result.get();
        System.out.println(clubMember);
        //  Hibernate:
        //    select
        //        clubmember0_.email as email1_0_,
        //        clubmember0_.moddate as moddate2_0_,
        //        clubmember0_.regdate as regdate3_0_,
        //        clubmember0_.from_social as from_soc4_0_,
        //        clubmember0_.name as name5_0_,
        //        clubmember0_.password as password6_0_,
        //        roleset1_.club_member_email as club_mem1_1_0__,
        //        roleset1_.role_set as role_set2_1_0__
        //    from
        //        club_member clubmember0_
        //    left outer join
        //        club_member_role_set roleset1_
        //            on clubmember0_.email=roleset1_.club_member_email
        //    where
        //        clubmember0_.from_social=?
        //        and clubmember0_.email=?
        //ClubMember(email=user95@testemail.com, password=$2a$10$EzFkcW2tXoCrU9/DiR2p6.8b20x8qlkiSdGdfO2o08jl/qt76TFGy, name=사용자95, fromSocial=false, roleSet=[ADMIN, MANAGER, USER])
    }

}
