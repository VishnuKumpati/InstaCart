package com.instacart.service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.instacart.dao.AdminDaoInterface;
import com.instacart.dao.BuyerDaoInterface;
import com.instacart.dao.RetailerDaoInterface;
import com.instacart.entities.Admin;
import com.instacart.entities.Buyer;
import com.instacart.entities.Retailer;
import com.instacart.entities.UserDTO;

import jakarta.transaction.Transactional;
@Service
public class LoginServiceImpl implements LoginServiceInterface{
@Autowired
private AdminDaoInterface adminDao;
@Autowired
private RetailerDaoInterface retailerDao;
@Autowired
private BuyerDaoInterface buyerDao;

	@Override
	@Transactional
	public String registerUser(UserDTO user) {
		 // Check if email or contact number already exists
        if (adminDao.existsByDetails(user.getEmail(),user.getContactNumber()) || 
            buyerDao.existsByDetails(user.getEmail(),user.getContactNumber()) ||
            retailerDao.existsByDetails(user.getEmail(),user.getContactNumber())) {
            return "Email already registered.";
        }
        System.out.println(user.getUserType());
        if ("admin".equals(user.getUserType())) {
            if (!"9".equals(user.getAdminPassword())) {
                return "Invalid admin password.";
            }
            Admin admin = new Admin();
            admin.setName(user.getName());
            admin.setEmail(user.getEmail());
            admin.setContactNumber(user.getContactNumber());
            admin.setPassword(user.getPassword());
            adminDao.save(admin);
        } else if ("customer".equals(user.getUserType())) {
        	System.out.println("user buyer");
            Buyer buyer = new Buyer();
            buyer.setName(user.getName());
            buyer.setEmail(user.getEmail());
            buyer.setPassword(user.getPassword());
            buyer.setAge(user.getAge());
            buyer.setContactNumber(user.getContactNumber());
            buyer.setCity(user.getCity());
            buyer.setUserType(user.getUserType());
            buyerDao.save(buyer);
        } else if ("retailer".equals(user.getUserType())) {
        	System.out.println("user buyer");
            Retailer retailer = new Retailer();
            retailer.setName(user.getName());
            retailer.setEmail(user.getEmail());
            retailer.setContactNumber(user.getContactNumber());
            retailer.setCity(user.getCity());
            retailer.setGstNumber(user.getGstNumber());
            retailer.setAadharNumber(user.getAadharNumber());
            retailer.setPanNumber(user.getPanNumber());
            retailer.setContactNumber(user.getContactNumber());
            retailer.setPassword(user.getPassword());
            System.out.println("success");
            retailerDao.save(retailer);
        } else {
            return "Invalid user type.";
        }

        return "success";
    }

	 public Object authenticateUser(String email, String password) {
	        // Authenticate Admin
	        Admin admin = adminDao.findByEmailAndPassword(email, password);
	        if (admin != null) return admin;

	        // Authenticate Buyer
	        Buyer buyer = buyerDao.findByEmailAndPassword(email, password);
	        if (buyer != null) {
	            if ("blocked".equalsIgnoreCase(buyer.getStatus())){
	                return "blocked";
	            }
	            return buyer;
	        }

	        // Authenticate Retailer
	        Retailer retailer = retailerDao.findByEmailAndPassword(email, password);
	        if (retailer != null) {
	            if ("blocked".equalsIgnoreCase(retailer.getStatus())) {
	                return "blocked";
	            }
	            return retailer;
	        }

	        return null; // User not found
	    }


	

}
