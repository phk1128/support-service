package com.example.supportservice.domain.member.model;

import com.example.supportservice.domain.point.model.Point;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @Column(name="MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    private final LocalDateTime joinDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private MemberRoleEnum role;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    private MemberJoinType joinType;


    @Builder
    public Member(String email, String name , String password, MemberStatus status, MemberRoleEnum role, MemberJoinType joinType) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.status = status;
        this.role = role;
        this.joinType = joinType;
    }

    public void updatePassword(String password){
        this.password = password;

    }


    public void update(MemberStatus status, MemberRoleEnum role) {

        this.role = role;
        this.status = status;
    }



}
