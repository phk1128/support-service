package com.example.supportservice.domain.card.model;


import com.example.supportservice.domain.member.model.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "CARD")
@Getter
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CARD_ID")
    private Long id;

    private String cardNumber;

    private CardCompany cardCompany;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;


    @Builder
    public Card(String cardNumber, CardCompany cardCompany, Member member) {

        this.cardNumber = cardNumber;
        this.cardCompany = cardCompany;
        this.member = member;

    }



}
