package com.instacart.service;

import com.instacart.dao.AdminDaoInterface;
import com.instacart.entities.Buyer;
import com.instacart.entities.Retailer;
import com.instacart.entities.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminServiceImplementation implements AdminServiceInterface {

    @Autowired
    private AdminDaoInterface adminDao;

    @Override
    public List<Buyer> getAllUsers() {
        return adminDao.findAllUsers();
    }

    @Override
    public List<Retailer> getAllRetailers() {
        return adminDao.findAllRetailers();
    }

    @Override
    public void blockUser(Long id) {
        adminDao.blockUser(id);
    }

    @Override
    public void unblockUser(Long id) {
        adminDao.unblockUser(id);
    }

    @Override
    public void blockRetailer(Long id) {
        adminDao.blockRetailer(id);
    }

    @Override
    public void unblockRetailer(Long id) {
        adminDao.unblockRetailer(id);
    }

    @Override
    public List<Complaint> viewComplaints() {
        return adminDao.viewComplaints();
    }

    @Override
    public List<Retailer> viewRegistrationRequests() {
        return adminDao.viewRegistrationRequests();
    }
}
