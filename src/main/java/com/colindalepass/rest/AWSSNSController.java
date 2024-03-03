//package com.colindalepass.rest;
//
//import com.colindalepass.dto.SMSRequestDTO;
//import com.colindalepass.service.AWSSNSService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("notification")
//public class AWSSNSController {
//
//    @Autowired
//    AWSSNSService snsService;
//
//
////working method
//    @PostMapping("subscribe/{mobile}")
//    public String sendSMS(@PathVariable(value = "mobile")String mobile) {
//        String response = snsService.subscribeMobile(mobile);
//        return String.format("SNS Response: %s", response);
//    }
//
//
//
//    @PostMapping("customer-sms")
//    public String sendCustomerSMS(@RequestBody SMSRequestDTO request) {
//        String response = snsService.publishMessageToSNSTopic(request.getMessage(), request.getMobile());
//        return String.format("SNS Response: %s", response);
//    }
//
//    @PostMapping("campaign-sms")
//    public String sendCampaignSMS(@RequestBody SMSRequestDTO request) {
//        String response = snsService.publishMessageToSNSTopic(request.getMessage());
//        return String.format("SNS Response: %s", response);
//    }
//
//}
