package com.instacart.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // Corrected import for Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.instacart.entities.Admin;
import com.instacart.entities.Buyer;
import com.instacart.entities.Retailer;
import com.instacart.entities.UserDTO;
import com.instacart.service.LoginServiceInterface;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginRegisterController {
    @Autowired
    private LoginServiceInterface service;

    @RequestMapping("/")
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

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute UserDTO user, Model model) {
        String result = service.registerUser(user);
        if ("success".equals(result)) {
            model.addAttribute("message", "Registration successful! You can now login.");
            return "success"; // Return to success page
        } else {
            model.addAttribute("error", result);
            return "register"; // Return to registration page with error
        }
}
    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
        Object user = service.authenticateUser(email, password);

        if (user == null) {
            model.addAttribute("errorMessage", "Not so fast! Register first if you want to be part of the bookworm family!");
            return "login"; // login.jsp
        }

        if ("blocked".equals(user)) {
            model.addAttribute("errorMessage", "Your account has been blocked. Please contact support.");
            return "login"; // login.jsp
        }

        return handleUserRedirect(user, session);
    }

    private String handleUserRedirect(Object user, HttpSession session) {
        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            setSessionAttributes(session, admin);
            return "adminDashboard"; // adminDashboard.jsp
        } else if (user instanceof Buyer) {
            Buyer buyer = (Buyer) user;
            setSessionAttributes(session, buyer);
            return "redirect:/books"; // getAllBooksServlet
        } else if (user instanceof Retailer) {
            Retailer retailer = (Retailer) user;
            setSessionAttributes(session, retailer);
            return "redirect:/retailerDashboard"; // retailerDashBoard.jsp
        }
        return "login"; // login.jsp
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
}