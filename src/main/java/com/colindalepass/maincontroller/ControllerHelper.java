package com.colindalepass.maincontroller;

import com.colindalepass.config.Utility;
import com.colindalepass.entity.User;
import com.colindalepass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import jakarta.servlet.http.HttpServletRequest;

@Component
public class ControllerHelper {
	@Autowired private UserService userService;
	
	public User getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		return userService.getByEmail(email);
	}
}
