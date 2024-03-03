package com.colindalepass.security;

import java.io.IOException;

//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import com.colindalepass.config.ColindalePassUserDetails;
import com.colindalepass.entity.AuthenticationType;
import com.colindalepass.entity.User;
import com.colindalepass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;



import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired private UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		ColindalePassUserDetails userDetails = (ColindalePassUserDetails) authentication.getPrincipal();
		User user = userDetails.getUser();

//		userService.updateAuthenticationType(user, AuthenticationType.DATABASE);

		super.onAuthenticationSuccess(request, response, authentication);
	}


}
