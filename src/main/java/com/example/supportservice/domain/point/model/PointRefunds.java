package com.example.supportservice.domain.point.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "POINT_REFUNDS")
@Getter
@NoArgsConstructor
public class PointRefunds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POINT_REFUNDS_ID")
    private Long id;

    private LocalDateTime completionDate;

    private LocalDateTime requestDate;

    private PointRefundsBank bank;

    private String account;

    private Integer amount;

    private PointRefundsStatus status;

    @ManyToOne
    @JoinColumn(name = "POINT_ID",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Point point;

    @Builder
    public PointRefunds(PointRefundsBank bank, String account, Integer amount, LocalDateTime requestDate) {
        this.bank = bank;
        this.account = account;
        this.amount = amount;
        this.requestDate = requestDate;

    }

    public void update(PointRefundsStatus status, LocalDateTime completionDate) {
        this.status = status;
        this.completionDate = completionDate;

    }








}
