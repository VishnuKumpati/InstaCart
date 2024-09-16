package com.instacart.service;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import com.instacart.dao.AdminDaoInterface;
import com.instacart.dao.BuyerDaoInterface;
import com.instacart.dao.RetailerDaoInterface;
import com.instacart.entities.Admin;
import com.instacart.entities.Buyer;
import com.instacart.entities.Retailer;
import com.instacart.entities.UserDTO;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginServiceImpl implements LoginServiceInterface {
	@Autowired
	private HttpServletRequest request;

    @Autowired
    private AdminDaoInterface adminDao;

    @Autowired
    private RetailerDaoInterface retailerDao;

    @Autowired
    private BuyerDaoInterface buyerDao;

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> otpStore = new ConcurrentHashMap<>();
    private Map<String, Long> otpExpiry = new ConcurrentHashMap<>();
    
    private static final long OTP_EXPIRY_TIME = 5 * 60 * 1000; // 5 minutes

    @Override
    @Transactional
    public String registerUser(UserDTO user) {
        // Check if email or contact number already exists
        if (adminDao.existsByDetails(user.getEmail(), user.getContactNo()) || 
            buyerDao.existsByDetails(user.getEmail(), user.getContactNo()) ||
            retailerDao.existsByDetails(user.getEmail(), user.getContactNo())) {
            return "Email already registered.";
        }

        if ("admin".equals(user.getUserType())) {
            if (!"9".equals(user.getAdminPassword())) {
                return "Invalid admin password.";
            }
            Admin admin = new Admin();
            admin.setName(user.getName());
            admin.setEmail(user.getEmail());
            admin.setContactNumber(user.getContactNo());
           admin.setUserType(user.getUserType());
            admin.setPassword(user.getPassword());
            adminDao.save(admin);
        } else if ("customer".equals(user.getUserType())) {
            Buyer buyer = new Buyer();
            buyer.setName(user.getName());
            buyer.setEmail(user.getEmail());
            buyer.setPassword(user.getPassword());
            buyer.setAge(user.getAge());
            buyer.setContactNumber(user.getContactNo());
            System.out.println("contact number"+user.getContactNo());
            buyer.setCity(user.getCity());
            buyer.setUserType(user.getUserType());
            buyerDao.save(buyer);
        } else if ("retailer".equals(user.getUserType())) {
            Retailer retailer = new Retailer();
            retailer.setName(user.getName());
            retailer.setEmail(user.getEmail());
            retailer.setContactNumber(user.getContactNo());
            retailer.setCity(user.getCity());
            retailer.setGstNumber(user.getGstNumber());
            retailer.setAadharNumber(user.getAadharNumber());
            retailer.setPanNumber(user.getPanNumber());
            retailer.setContactNumber(user.getContactNo());
            retailer.setPassword(user.getPassword());
            retailer.setUserType(user.getUserType());
            retailerDao.save(retailer);
        } else {
            return "Invalid user type.";
        }

        return "success";
    }

    @Override
    public Object authenticateUser(String email, String password) {
        // Authenticate Admin
        Admin admin = adminDao.findByEmailAndPassword(email, password);
        if (admin != null) return admin;

        // Authenticate Buyer
        Buyer buyer = buyerDao.findByEmailAndPassword(email, password);
        if (buyer != null) {
            if ("blocked".equalsIgnoreCase(buyer.getStatus())) {
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

    @Override
    public boolean sendOtp(String email, String password) {
        // Authenticate the user
        Object user = authenticateUser(email, password);

        if (user != null && !(user instanceof String && "blocked".equals(user))) {
            // Generate and store OTP
            String otp = generateOtp();
            otpStore.put(email, otp);
            otpExpiry.put(email, System.currentTimeMillis() + OTP_EXPIRY_TIME);

            // Send OTP via email
            try {
                sendOtpEmail(email, otp);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        String storedOtp = otpStore.get(email);
        Long expiryTime = otpExpiry.get(email);

        if (storedOtp != null && storedOtp.equals(otp) && expiryTime != null && System.currentTimeMillis() <= expiryTime) {
            otpStore.remove(email); // OTP should be used only once
            otpExpiry.remove(email);
            return true;
        }
        return false;
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        System.out.println("otp genereted : "+otp);
        return String.valueOf(otp);
    }

    private void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is : " + otp);
        mailSender.send(message);
    }

    @Override
    public String passwordRecovery(String email) {
    	System.out.println(email);
        boolean emailExists = adminDao.passRecoverymail(email) ||
                              buyerDao.passRecoverymail(email) ||
                              retailerDao.passRecoverymail(email);

        if (emailExists) {
            String otp = generateOtp();
            otpStore.put(email, otp);
            otpExpiry.put(email, System.currentTimeMillis() + OTP_EXPIRY_TIME);

            sendOtpToEmail(email, otp);
            return "OTP sent";
        }
        return "Email not found";
    }

    private void sendOtpToEmail(String email, String otp) {
        // Create a SimpleMailMessage object
        SimpleMailMessage message = new SimpleMailMessage();
        
        // Set the recipient's email address
        message.setTo(email);
        
        // Set the subject of the email
        String subject = "Your OTP for Password Recovery";
        message.setSubject(subject);
        
        // Set the body of the email
        String text = "Your OTP is: " + otp + ". This OTP is valid for 10 minutes.";
        message.setText(text);
        
        // Send the email
        mailSender.send(message);
    }

	@Override
	public String updatePassword(String password) {
		HttpSession session=request.getSession();
		String userType=(String) session.getAttribute("userType");
		Long userId= (Long) session.getAttribute("userId");
		System.out.println(userType);
		System.out.println(userId);
		if (userType != null && userId!=null) {
	         
	        if (userType.equals("customer")) {
	          return buyerDao.updatePassword(password,userId);
	        } 
	        else if (userType.equals("retailer")) {
	        	 return retailerDao.updatePassword(password,userId);
	        }
	        else if(userType.equals("admin")){
	        	return adminDao.updatePassword(password,userId);
	        }
	        
	        
	    } else {
	        // Handle the case where userType is not found in session
	        throw new IllegalStateException("User type not found in session");
	    }
         return"not changed";
			
		}
		
	}



