//package com.example.supportservice.domain.supportLine.model;
//
//
//import com.example.supportservice.domain.organization.model.Organization;
//import com.example.supportservice.domain.support.model.Support;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "SUPPORT_LINE")
//@NoArgsConstructor
//@Getter
//public class SupportLine {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "SUPPORT_LINE_ID")
//    private Long id;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "SUPPORT_ID",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private Support support;
//
//    @ManyToOne
//    @JoinColumn(name = "ORGANIZATION_ID",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private Organization organization;
//
//    private Integer price;
//
//}
