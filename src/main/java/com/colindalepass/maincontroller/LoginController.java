package com.colindalepass.maincontroller;

import com.colindalepass.entity.Constants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.yaml.snakeyaml.scanner.Constant;

@Controller
public class LoginController {


    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/map")
    public String map(Model model) {
        model.addAttribute("S3_BASE_URI", Constants.S3_BASE_URI);
        return "map";
    }

    @GetMapping("/airport")
    public String airportmapping() {
        return   "airporthotel";
    }

    @GetMapping("/barracks")
    public String barracksmapping() {
        return   "barracks";
    }

    @GetMapping("/tejuosho")
    public String tejuoshomapping() {
        return   "tejuosho";
    }


}
