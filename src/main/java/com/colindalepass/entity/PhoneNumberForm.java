package com.colindalepass.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "phoneNumber")
public class PhoneNumberForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
