package com.example.club.entity;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ClubMember extends BaseEntity {

    @Id
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)
//    @Builder.Default
    private Set<ClubMemberRole> roleSet; // = new HashSet<>();//    //ClubMemberRole 타입값을 처리하기 위해 Set<ClubMemberRole> 추가


    public void addMemberRole(ClubMemberRole clubMemberRole){

        roleSet.add(clubMemberRole);
    }

}

//Hibernate:
//
//        create table club_member (
//        email varchar(255) not null,
//        moddate datetime(6),
//        regdate datetime(6),
//        from_social bit not null,
//        name varchar(255),
//        password varchar(255),
//        primary key (email)
//        ) engine=InnoDB
//        Hibernate:
//
//        create table club_member_role_set (
//        club_member_email varchar(255) not null,
//        role_set integer
//        ) engine=InnoDB
//        Hibernate:
//
//        alter table club_member_role_set
//        add constraint FKbfljk8ybtolixxc88osbodrek
//        foreign key (club_member_email)
//        references club_member (email)
