package com.colindalepass.service;


import com.colindalepass.config.GeneratedCodeResponse;
import com.colindalepass.entity.GeneratedCodeDetails;
import com.colindalepass.entity.User;
import com.colindalepass.exception.UserNotFoundException;
import com.colindalepass.repository.CodeDetailsRepository;
import com.colindalepass.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class CodeGeneratorService {


    @Autowired
    CodeDetailsRepository codeRepo;
    

    @Autowired
    UserRepository userRepo;


//    public GeneratedCodeDetails generateOTPAndTime() {
//
//        return generateOTP();
//    }

    public GeneratedCodeResponse generateOTPAndTime() {
        // Generate OTP and get details
        GeneratedCodeDetails otpDetails = generateOTP();

        // Return a response object with the required details
        return new GeneratedCodeResponse(
                otpDetails.getVerificationCode(),
                LocalDateTime.now(),  // Set the current time as created time
                otpDetails.getExpiryTime()
        );
    }

    @Scheduled(cron = "0 0 * * * *") // Runs every hour
    public void scheduleDeleteExpiredCodes() {
        deleteExpiredCodes();
    }


    public void deleteExpiredCodes() {
        LocalDateTime currentTime = LocalDateTime.now();
        codeRepo.deleteExpiredCodes(currentTime);
    }



//
////woring method
//    public String generateAndSaveOTP() {
//        // Generate OTP and get details
//        GeneratedCodeDetails otpDetails = generateOTP();
//
//        // Save OTP details in your storage (e.g., database)
//        saveOTPDetails(otpDetails);
//
//        // Return the OTP code
//        return otpDetails.getVerificationCode();
//    }

    private GeneratedCodeDetails generateOTP() {
        // Generate the OTP
        String otpCode = new DecimalFormat("000000").format(new Random().nextInt(999999));

        // Get the current time as the created time
        LocalDateTime createdTime = LocalDateTime.now();

        // Calculate expiry time (e.g., 1 hour from now)
        LocalDateTime expiryTime = LocalDateTime.now().plusHours(1);

        // Return OTPDetails object with code and expiry time
        return new GeneratedCodeDetails(otpCode, expiryTime, createdTime);
    }

    // This method should be implemented to save OTPDetails in your storage
//    private void saveOTPDetails(GeneratedCodeDetails otpDetails) {
//        // Implement the logic to save OTPDetails in your storage (e.g., database)
//        codeRepo.save(otpDetails);
//    }

    private GeneratedCodeDetails getOTPDetailsByCode(String verificationCode) {
        // Implement the logic to retrieve OTPDetails by code from your storage (e.g., database)
        // Return null if not found
        Optional<GeneratedCodeDetails> expectedCode = Optional.ofNullable(codeRepo.findByCode(verificationCode));
            // Assuming you have an ID to uniquely identify the OTPDetails record in the database


        // Record found, return the OTPDetails object
        // Record not found, handle accordingly
        // or throw an exception, return a default value, etc.
        return expectedCode.orElse(null);


    }




 // Working method to generate and safe code in database as at 2/1/2024

//    public String CodeGenerator(){
//
//        return generateOTP();
//    }


    public GeneratedCodeDetails save(GeneratedCodeDetails codeDetails){

        return codeRepo.save(codeDetails);
    }
//
//
//    private String generateOTP() {
//        return new DecimalFormat("000000")
//                .format(new Random().nextInt(999999));
//    }

    public User get(Integer id) throws UserNotFoundException {
        try {
            return  userRepo.findById(id).get();
        }catch (NoSuchElementException ex) {
            throw new UserNotFoundException("could not find any user with ID" + id);
        }
    }

    public User getByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }

    public Optional<User> getUserDetailsById(Integer id) {
        return userRepo.findUserDetailsById(id);
    }


}
