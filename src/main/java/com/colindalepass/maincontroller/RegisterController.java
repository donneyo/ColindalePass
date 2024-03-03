package com.colindalepass.maincontroller;


import com.colindalepass.config.Utility;
import com.colindalepass.entity.User;
import com.colindalepass.service.SettingService;
import com.colindalepass.service.UserService;
import com.colindalepass.setting.EmailSettingBag;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    private SettingService settingService;

    @PostMapping("/create_user")
    public String createCustomer(User user, Model model,
                                 HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        userService.registerUser(user);
        sendVerificationEmail(request, user);

        model.addAttribute("pageTitle", "Registration Succeeded!");

        return "register_success";
    }

//    @PostMapping("/create_user_without_email")
//    public String createCustomerwithoutEmail(User user, Model model,
//                                 HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
//        userService.registerUser(user);
////        sendVerificationEmail(request, user);
//
//        model.addAttribute("pageTitle", "Registration Succeeded!");
//
//        return "register_success";
//    }

    private void sendVerificationEmail(HttpServletRequest request, User user)
            throws UnsupportedEncodingException, MessagingException {
        EmailSettingBag emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);

        String toAddress = user.getEmail();
        String subject = emailSettings.getCustomerVerifySubject();
        String content = emailSettings.getCustomerVerifyContent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());

        String verifyURL = Utility.getSiteURL(request) + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("to Address: " + toAddress);
        System.out.println("Verify URL: " + verifyURL);
    }

}
