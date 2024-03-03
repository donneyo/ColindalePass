package com.colindalepass.config;

import java.time.LocalDateTime;

public class GeneratedCodeResponse {
    private String verificationCode;
    private LocalDateTime createdTime;
    private LocalDateTime expiryTime;

    public GeneratedCodeResponse(String verificationCode, LocalDateTime createdTime, LocalDateTime expiryTime) {
        this.verificationCode = verificationCode;
        this.createdTime = createdTime;
        this.expiryTime = expiryTime;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

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
}
