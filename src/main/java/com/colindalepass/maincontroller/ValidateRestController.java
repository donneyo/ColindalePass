package com.colindalepass.maincontroller;

import com.colindalepass.entity.GeneratedCodeDetails;
import com.colindalepass.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ValidateRestController {

    @Autowired
    ValidationService validationService;

    @PostMapping("/Validate_Code/check_doescode_match")
    public ResponseEntity<String> validateOtp(@RequestParam("verificationCode") String verificationCode) {
        // Retrieve the Attendees entity from the database based on verificationCode
        GeneratedCodeDetails result = validationService.doesCodeExist(verificationCode);

        // Check if the retrieved entity is not null and if the entered OTP is valid and not expired
        if (result != null && isOTPValidAndNotExpired(result, verificationCode)) {
            return ResponseEntity.ok(result.getFirstName());  // Return the first name if validation is successful
        } else if (result != null && result.getExpiryTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.ok("expired");  // Return "expired" if the code has expired
        } else {
            return ResponseEntity.ok("invalid");  // Return "invalid" if the code is invalid
        }
    }


    private boolean isOTPValidAndNotExpired(GeneratedCodeDetails result, String verificationCode) {

        return verificationCode != null && verificationCode.equals(result.getVerificationCode()) && !isExpired(result);
    }

    private boolean isExpired(GeneratedCodeDetails result) {
        // Check if the OTP is expired
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiryTime = result.getExpiryTime();
        return currentTime.isAfter(expiryTime);
    }

//working method
//    @PostMapping("/Validate_Code/check_doescode_match")
//    public ResponseEntity<String> validateOtp(
//
//            @RequestParam("verificationCode") String verificationCode) {
//
//        // Retrieve the Attendees entity from the database based on verificationCode
//        GeneratedCodeDetails result = validationService.doesCodeExist(verificationCode);
//
//        // Check if the retrieved entity is not null
//        if (result != null) {
//            return ResponseEntity.ok(result.getFirstName());  // Return the first name if validation is successful
//        } else {
//            return ResponseEntity.ok("false");
//        }
//    }


}
