package com.colindalepass.maincontroller;

import com.colindalepass.config.ColindalePassUserDetails;
import com.colindalepass.config.GeneratedCodeResponse;
import com.colindalepass.entity.GeneratedCodeDetails;
import com.colindalepass.entity.User;
import com.colindalepass.exception.UserNotFoundException;
import com.colindalepass.service.CodeGeneratorService;
import com.colindalepass.service.ValidationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Optional;

@Controller
public class CodeGeneratorController {

    @Autowired
    CodeGeneratorService codeGeneratorService;

    @Autowired
    ValidationService validateSerice;

    @GetMapping("/Generate-Code-Page")
    public String viewGenerateCodeForm(@AuthenticationPrincipal ColindalePassUserDetails loggedUser,Model model)
            throws UserNotFoundException {

        String email = loggedUser.getUsername();
        User user = codeGeneratorService.getByEmail(email);
        model.addAttribute("user", user);

        return "Generate_code";
    }

//modified method
@PostMapping("/generateCode")
public String generateCode(@AuthenticationPrincipal ColindalePassUserDetails loggedUser, Model model) throws UserNotFoundException {
    String email = loggedUser.getUsername();
    User user = codeGeneratorService.getByEmail(email);

    // Call the generateOTPAndTime method
    GeneratedCodeResponse codeResponse = codeGeneratorService.generateOTPAndTime();

    // Use the details from the response object
    model.addAttribute("code", codeResponse.getVerificationCode());
    model.addAttribute("user", user);

    // Create an instance of GeneratedCodeDetails for saving
    GeneratedCodeDetails codeDetails = new GeneratedCodeDetails();
    codeDetails.setId(user.getId());
    codeDetails.setEmail(user.getEmail());
    codeDetails.setFirstName(user.getFirstName());
    codeDetails.setLastName(user.getLastName());
    codeDetails.setVerificationCode(codeResponse.getVerificationCode());
    codeDetails.setCreatedTime(codeResponse.getCreatedTime());
    codeDetails.setExpiryTime(codeResponse.getExpiryTime());

    // Save data of the current user and the generated code plus time
    codeGeneratorService.save(codeDetails);

    return "Generate_code";
}

//    @GetMapping("/add-phoneNumber")
//            public String addPhoneNumber(Model model){
//
//            return "test";
//    }

    @GetMapping("/add-phoneNumber")
    public String addPhoneNumber(Model model) {
        model.addAttribute("mobile", "mobile"); // Replace with your logic to get the mobile value
        return "test";
    }



//working method
//    @GetMapping("/generateCode")
//        public String generateCode(@AuthenticationPrincipal ColindalePassUserDetails loggedUser,Model model) throws UserNotFoundException {
//        String email = loggedUser.getUsername();
//        User user = codeGeneratorService.getByEmail(email);
//        String code = codeGeneratorService.CodeGenerator();
//
//        model.addAttribute("code", code);
//        model.addAttribute("user", user);
//
//        // Create an instance of GeneratedCodeDetails
//        GeneratedCodeDetails codeDetails = new GeneratedCodeDetails();
//        codeDetails.setId(user.getId());
//        codeDetails.setEmail(user.getEmail()); // Assuming you have a setUser method in GeneratedCodeDetails
//        codeDetails.setFirstName(user.getFirstName());
//        codeDetails.setLastName(user.getLastName());
//
//        codeDetails.setCreatedTime(new Date());
//        codeDetails.setVerificationCode(code);
//
//        // write method to save data of current logger and the generated code
//        codeGeneratorService.save(codeDetails);
//        return "Generate_code";
//    }

    @GetMapping("/Validate_Code")
    public String ValidationPage(Model model){

        return "Validate_code";
    }

    //controller to check doesCodematch method from service layer
//    @PostMapping("/checkCode")
//    public String validateOTP(@RequestParam("verificationCode") String verificationCode) {
//        boolean codesMatch = validateSerice.doesCodeMatch(verificationCode);
//        if (codesMatch) {
//            return "valid_code";
//        }else {
//            return "Invalid_code";
//        }
//    }








//    @GetMapping("/generateCode")
//    public String generateCode(@AuthenticationPrincipal ColindalePassUserDetails loggedUser,
//                               Model model) throws UserNotFoundException {
//        String code = codeGeneratorService.CodeGenerator();
//        String email = loggedUser.getUsername();
//        User user = codeGeneratorService.getByEmail(email);
//        model.addAttribute("user", user);
//          model.addAttribute("code", code);
//
//        return "Generate_code";
//    }




}

