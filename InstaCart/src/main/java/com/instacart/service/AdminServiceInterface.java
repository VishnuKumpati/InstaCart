package com.instacart.service;


import com.instacart.entities.Buyer;
import com.instacart.entities.Retailer;
import com.instacart.entities.Complaint;
import java.util.List;

public interface AdminServiceInterface {
    List<Buyer> getAllUsers();
    List<Retailer> getAllRetailers();
    void blockUser(Long id);
    void unblockUser(Long id);
    void blockRetailer(Long id);
    void unblockRetailer(Long id);
    List<Complaint> viewComplaints();
    List<Retailer> viewRegistrationRequests();
}
