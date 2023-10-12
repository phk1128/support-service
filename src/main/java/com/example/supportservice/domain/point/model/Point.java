package com.example.supportservice.domain.point.model;


import com.example.supportservice.domain.member.model.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import javax.persistence.*;

@Entity
@Table(name = "POINT")
@Getter
@NoArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POINT_ID")
    private Long id;

    private Integer chargedPoint;

    private Integer remainPoint;

    private Integer usedPoint;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;


    @Builder
    public Point(Integer chargedPoint, Integer remainPoint, Integer usedPoint) {

        this.chargedPoint = chargedPoint;
        this.remainPoint = remainPoint;
        this.usedPoint = usedPoint;
    }


    public void updateRemainAndUsedPoint (Integer usedPoint, Integer remainPoint) {

        this.remainPoint = remainPoint;
        this.usedPoint = usedPoint;

    }

    public void chargePoint (Integer point) {

        this.chargedPoint += point;
        this.remainPoint += point;

    }

    public void setMember(Member member) {

        this.member = member;

    }

}
