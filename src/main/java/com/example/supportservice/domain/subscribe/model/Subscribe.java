package com.example.supportservice.domain.subscribe.model;


import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.organization.model.Organization;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SUBSCRIBE")
@Getter
@NoArgsConstructor
public class Subscribe {


    @Id
    @Column(name = "SUBSCRIBE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private SubscribeStatus status;

    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Organization organization;

    @Builder
    public Subscribe(LocalDateTime startDate, LocalDateTime endDate, SubscribeStatus status, Integer amount, Member member, Organization organization) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.amount = amount;
        this.member = member;
        this.organization = organization;
    }

    public void update(LocalDateTime startDate, LocalDateTime endDate, SubscribeStatus status) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;

    }

}
