package com.instacart.controller;

import com.instacart.entities.Buyer;
import com.instacart.entities.Retailer;
import com.instacart.entities.Complaint;
import com.instacart.service.AdminServiceInterface;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminServiceInterface adminService;
    

    @GetMapping("/users")
    public List<Buyer> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/retailers")
    public List<Retailer> getAllRetailers() {
        return adminService.getAllRetailers();
    }

    @PutMapping("/users/block/{id}")
    public void blockUser(@PathVariable Long id) {
        adminService.blockUser(id);
    }

    @PutMapping("/users/unblock/{id}")
    public void unblockUser(@PathVariable Long id) {
        adminService.unblockUser(id);
    }

    @PutMapping("/retailers/block/{id}")
    public void blockRetailer(@PathVariable Long id) {
        adminService.blockRetailer(id);
    }

    @PutMapping("/retailers/unblock/{id}")
    public void unblockRetailer(@PathVariable Long id) {
        adminService.unblockRetailer(id);
    }

    @GetMapping("/complaints")
    public List<Complaint> viewComplaints() {
        return adminService.viewComplaints();
    }

    @GetMapping("/registration-requests")
    public List<Retailer> viewRegistrationRequests() {
        return adminService.viewRegistrationRequests();
    }
}
