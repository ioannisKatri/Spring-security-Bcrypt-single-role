package com.ioankat.config.security;

import com.ioankat.entity.User;
import com.ioankat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		System.out.println("\n\nIn customAuthenticationSuccessHandler\n\n");

		String userName = authentication.getName();
		
		System.out.println("userName=" + userName);

		User theUser = userService.findByUserName(userName);
		// now place in the session
		HttpSession session = request.getSession();
		session.setAttribute("user", theUser);
		session.setAttribute("dog", "Random-Dog");

		System.out.println("After setting DOG and USER");
		System.out.println(session.getAttribute("user"));
		// forward to home page
		
		response.sendRedirect(request.getContextPath() + "/");
	}

}
