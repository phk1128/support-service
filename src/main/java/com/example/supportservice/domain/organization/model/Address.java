package com.example.supportservice.domain.organization.model;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;


    /*기본 생성자의 접근제한자를 protected로 설정하여 city, street, zipcode가 비어있는 Address 객체가 생성 되는 것을 방지
    즉, Address 객체는 반드시 매개변수를 받게끔 하는것이 목적*/
    protected Address() {

    }

    // 매개변수를 받는 생성자의 경우 접근제한자가 public이므로 상속없이 Address 객체를 생성가능
    public Address(String city, String street, String zipcode) {

        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
