package com.instacart.controller;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.servlet.http.HttpSession;

import com.instacart.entities.Admin;
import com.instacart.entities.Buyer;
import com.instacart.entities.Retailer;
import com.instacart.entities.UserDTO;
import com.instacart.service.LoginServiceInterface;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class LoginRegisterController {

    @Autowired
    private LoginServiceInterface service;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @GetMapping("/passwordRecovery")
    public String passwordRecoveryForm(Model model) {
        return "recoveryPassword";
    }

    @PostMapping("/registerUser")
    public String registerUser(UserDTO user, Model model) {
        String result = service.registerUser(user);
        if ("success".equals(result)) {
            model.addAttribute("message", "Registration successful! You can now login.");
            return "success";
        } else {
            model.addAttribute("error", result);
            return "register";
        }
    }

    @PostMapping("/sendOtp")
    @ResponseBody
    public String sendOtp(@RequestParam("email") String email, @RequestParam("password") String password) {
        boolean otpSent = service.sendOtp(email, password);
        if (otpSent) {
            return "{\"success\":true, \"message\":\"OTP has been sent to your registered email.\"}";
        } else {
            return "{\"success\":false, \"message\":\"Invalid email or password.\"}";
        }
    }

    @PostMapping("/verifyOtp")
    @ResponseBody
    public String verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String otp) {
    	System.out.println(email);
    	System.out.println(otp);
        boolean otpVerified = service.verifyOtp(email, otp);
        if (otpVerified) {
            return "{\"success\":true, \"message\":\"OTP verified successfully.\"}";
        } else {
            return "{\"success\":false, \"message\":\"Invalid OTP.\"}";
        }
    }

    @PostMapping("/validateOtp")
    public String validateOtp(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
        Object user = service.authenticateUser(email, password);

        if (user == null) {
            model.addAttribute("errorMessage", "Invalid email or password.");
            return "login";
        }

        if ("blocked".equals(user)) {
            model.addAttribute("errorMessage", "Your account has been blocked. Please contact support.");
            return "login";
        }

        return handleUserRedirect(user, session);
    }

    private String handleUserRedirect(Object user, HttpSession session) {
        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            setSessionAttributes(session, admin);
            return "redirect:/adminDashboard";
        } else if (user instanceof Buyer) {
            Buyer buyer = (Buyer) user;
            setSessionAttributes(session, buyer);
            return "redirect:/buyerDashboard";
        } else if (user instanceof Retailer) {
            Retailer retailer = (Retailer) user;
            setSessionAttributes(session, retailer);
            return "redirect:/retailerDashboard";
        }
        return "redirect:/login";
    }

    private void setSessionAttributes(HttpSession session, Object user) {
        session.setAttribute("user", user);
        session.setAttribute("userName", getUserName(user));
        session.setAttribute("userId", getUserId(user));
    }

    private String getUserName(Object user) {
        if (user instanceof Admin) return ((Admin) user).getName();
        if (user instanceof Buyer) return ((Buyer) user).getName();
        if (user instanceof Retailer) return ((Retailer) user).getName();
        return null;
    }

    private Long getUserId(Object user) {
        if (user instanceof Admin) return ((Admin) user).getId();
        if (user instanceof Buyer) return ((Buyer) user).getId();
        if (user instanceof Retailer) return ((Retailer) user).getId();
        return null;
    }

    @PostMapping("/passwordRecovery")
    @ResponseBody
    public Map<String, Object> passwordRecovery(@RequestParam("email") String email,HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        session.setAttribute("email",email);
        String message = service.passwordRecovery(email);

        if ("OTP sent".equals(message)) {
            response.put("success", true);
            response.put("message", "An OTP has been sent to your email. Please check your inbox.");
        } else {
            response.put("success", false);
            response.put("message", "The email address is not registered in our system. Please try again.");
        }

        return response;
    }
    
    @GetMapping("/newPassword")
    public String getMethodName() {
        return "newPassword";
    }
    
    @PostMapping("/updatePassword")
     public String passwordUpadte(@RequestParam String password,@RequestParam String confirmPassword) {
    	if(password.equals(confirmPassword)) {
    	 String msg=service.updatePassword(password);
    	 System.out.println(msg);
    	 return "success";
    	}
       return"error";
    }
    
  
}
