package com.colindalepass.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "codedetails")
public class GeneratedCodeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,  length = 45)
    private String email;


    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "verification_code", unique = true)
    private String verificationCode;



    @Column(name = "expiried_time", nullable = false)
    private LocalDateTime expiryTime;


    @Column(name = "created_time")
    private LocalDateTime createdTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

//    public Date getCreatedTime() {
//        return createdTime;
//    }
//
//    public void setCreatedTime(Date createdTime) {
//        this.createdTime = createdTime;
//    }


    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public GeneratedCodeDetails() {
    }

    public GeneratedCodeDetails(String firstName, String verificationCode) {
        this.firstName = firstName;
        this.verificationCode = verificationCode;
    }


    public GeneratedCodeDetails(String verificationCode, LocalDateTime expiryTime, LocalDateTime createdTime) {
        this.verificationCode = verificationCode;
        this.expiryTime = expiryTime;
        this.createdTime = createdTime;
    }


    @Override
    public String toString() {
        return "GeneratedCodeDetails{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
