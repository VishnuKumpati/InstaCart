package com.instacart.service;

import com.instacart.entities.UserDTO;

public interface LoginServiceInterface {

	String registerUser(UserDTO user);
//	 boolean sendOtp(String email, String password);
//	    boolean verifyOtp(String email, String otp);

	Object authenticateUser(String email, String password);

}
