package com.example.supportservice.domain.organization.model;


import lombok.Builder;
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

    private String url;

    @Enumerated(EnumType.STRING)
    private OrganizationStatus status;

    @Embedded
    private Address address;

    @Builder
    public Organization(String name, String contact, String url, OrganizationStatus status, Address address) {
        this.name = name;
        this.contact = contact;
        this.url = url;
        this.status = status;
        this.address = address;

    }

    public void update(String name, String contact, String url, OrganizationStatus status, Address address) {
        this.name = name;
        this.contact = contact;
        this.url = url;
        this.status = status;
        this.address = address;
    }


}
