package com.example.supportservice.domain.support.model;


import com.example.supportservice.domain.member.model.Member;

import com.example.supportservice.domain.organization.model.Organization;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SUPPORT")
@Getter
@NoArgsConstructor
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUPPORT_ID")
    private Long id;

    private LocalDateTime supportDate;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private SupportStatus status;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Organization organization;


    @Builder
    public Support (LocalDateTime supportDate, Integer amount, Member member, Organization organization){

        this.supportDate = supportDate;
        this.amount = amount;
        this.member = member;
        this.organization = organization;

    }

    public void updateStatus(SupportStatus status){
        this.status =status;

    }



}
