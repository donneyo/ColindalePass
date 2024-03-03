package com.colindalepass.maincontroller;

import com.colindalepass.config.ColindalePassUserDetails;
import com.colindalepass.entity.PhoneNumberForm;
import com.colindalepass.entity.User;
import com.colindalepass.service.CodeGeneratorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PhoneNumberController {

    @Autowired
    CodeGeneratorService codeGeneratorService;

    @GetMapping("/phone-number-input")
    public String showPhoneNumberInputPage( @AuthenticationPrincipal ColindalePassUserDetails loggedUser, Model model, HttpSession session) {

        String email = loggedUser.getUsername();
        User user = codeGeneratorService.getByEmail(email);
        model.addAttribute("user", user);
//        model.addAttribute("CodeFromFirstPage", code);

        return "PhoneNumInputPage";
    }


    //    @GetMapping("/generateCode")
//    public String generateCode(@AuthenticationPrincipal ColindalePassUserDetails loggedUser,
//                               Model model) throws UserNotFoundException {
//        String code = codeGeneratorService.CodeGenerator();
//        String email = loggedUser.getUsername();
//        User user = codeGeneratorService.getByEmail(email);
//        model.addAttribute("user", user);
//
//        return "Generate_code";
//    }

    @PostMapping("/send-sms")
    public String sendSms(@RequestParam("code") String code,@AuthenticationPrincipal ColindalePassUserDetails loggedUser,
                         Model model){
        String email = loggedUser.getUsername();
        User user = codeGeneratorService.getByEmail(email);
        model.addAttribute("user", user);
        model.addAttribute("CodeFromFirstPage", code);

    return "sms-successful-page";
    }


}
