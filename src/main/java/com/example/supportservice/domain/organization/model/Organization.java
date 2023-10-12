package com.example.supportservice.domain.organization.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ORGANIZATION")
@Getter
@NoArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORGANIZATION_ID")
    private Long id;

    private String name;

    private String contact;

    @Enumerated(EnumType.STRING)
    private OrganizationStatus status;

    @Embedded
    private Address address;


}
