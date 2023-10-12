package com.example.supportservice.domain.payment.model;


import com.example.supportservice.domain.member.model.Member;
import com.example.supportservice.domain.subscribe.model.Subscribe;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT")
@Getter
@NoArgsConstructor
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    private Integer amount;

    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentItem item;


    @ManyToOne
    @JoinColumn(name = "SUBSCRIBE_ID",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Subscribe subscribe;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    public void setSubscribe(Subscribe subscribe) {
        this.subscribe = subscribe;
    }

    public void updateStatus(PaymentStatus status) {
        this.status = status;
    }


    @Builder
    public Payment(PaymentType type, Integer amount, LocalDateTime paymentDate, Member member, PaymentItem item){
        this.type = type;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.member = member;
        this.item = item;
    }


}
