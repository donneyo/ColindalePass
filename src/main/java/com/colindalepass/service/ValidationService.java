package com.colindalepass.service;


import com.colindalepass.entity.GeneratedCodeDetails;
import com.colindalepass.repository.CodeDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class ValidationService {

    @Autowired
    private CodeDetailsRepository CodeDetailRepo;

    //chatGTP validation method
//    public boolean validateOTP(String enteredOTP) {
//        // Retrieve OTP details including the expiry time from storage
//        OTPDetails otpDetails = getOTPDetailsByCode(enteredOTP);
//
//        if (otpDetails != null) {
//            // Check if the current time is before the expiry time
//            LocalDateTime currentTime = LocalDateTime.now();
//            LocalDateTime expiryTime = otpDetails.getExpiryTime();
//
//            if (currentTime.isBefore(expiryTime)) {
//                // Valid OTP
//                return true;
//            } else {
//                // Expired OTP, you may optionally remove it from storage
//                removeExpiredOTP(otpDetails);
//            }
//        }
//
//        // Invalid or expired OTP
//        return false;
//    }



//working method for valid and invalid pages when not using javascript
//    public boolean doesCodeMatch(String verificationCode) {
//        GeneratedCodeDetails expectedCode = CodeDetailRepo.findByCode(verificationCode);
//
//
//        if (expectedCode != null) {
//            String newExpectedcode = expectedCode.getVerificationCode();
//            String regexPattern = "\\d{6}";
//
//            Pattern pattern = Pattern.compile(regexPattern);
//            Matcher matcher = pattern.matcher(verificationCode);
//
//            boolean codesMatch = matcher.matches() && verificationCode.equals(newExpectedcode);
//
//            return codesMatch;
//        }
//
//        return false;
//    }


    public GeneratedCodeDetails doesCodeExist(String verificationCode) {

        GeneratedCodeDetails databaseCode = CodeDetailRepo.findByCode(verificationCode);

        // Check if databaseCode is not null and if the retrieved verificationCode matches the provided verificationCode
        if (databaseCode != null && verificationCode.equals(databaseCode.getVerificationCode())) {
            System.out.println(databaseCode); // This will call the existing toString method
            return databaseCode;  // Returning the retrieved Attendees entity
        } else {
            return null;
        }
    }


}