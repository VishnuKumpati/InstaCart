package com.instacart.dao;

import com.instacart.entities.Buyer;
import com.instacart.entities.Retailer;
import com.instacart.entities.Complaint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDaoImplementation implements AdminDaoInterface {
	
	@Autowired
	private SessionFactory sf;

    @Override
    public List<Buyer> findAllUsers() {
        Session session = sf.openSession();
        List<Buyer> buyers = session.createQuery("FROM Buyer", Buyer.class).list();
        session.close();
        return buyers;
    }

    @Override
    public List<Retailer> findAllRetailers() {
    	Session session = sf.openSession();
        List<Retailer> retailers = session.createQuery("FROM Retailer", Retailer.class).list();
        session.close();
        return retailers;
    }

    @Override
    public void blockUser(Long id) {
    	Session session = sf.openSession();
    	Transaction tx = session.beginTransaction();
        Buyer buyer = session.get(Buyer.class, id);
        if (buyer != null) {
            buyer.setStatus("blocked");
            session.update(buyer);
        }
        tx.commit();
        session.close();
    }

    @Override
    public void unblockUser(Long id) {
    	Session session = sf.openSession();

        Transaction tx = session.beginTransaction();
        Buyer buyer = session.get(Buyer.class, id);
        if (buyer != null) {
            buyer.setStatus("active");
            session.update(buyer);
        }
        tx.commit();
        session.close();
    }

    @Override
    public void blockRetailer(Long id) {
    	Session session = sf.openSession();

        Transaction tx = session.beginTransaction();
        Retailer retailer = session.get(Retailer.class, id);
        if (retailer != null) {
            retailer.setActive(false);
            session.update(retailer);
        }
        tx.commit();
        session.close();
    }

    @Override
    public void unblockRetailer(Long id) {
    	Session session = sf.openSession();

        Transaction tx = session.beginTransaction();
        Retailer retailer = session.get(Retailer.class, id);
        if (retailer != null) {
            retailer.setActive(true);
            session.update(retailer);
        }
        tx.commit();
        session.close();
    }

    @Override
    public List<Complaint> viewComplaints() {
    	Session session = sf.openSession();

        List<Complaint> complaints = session.createQuery("FROM Complaint", Complaint.class).list();
        session.close();
        return complaints;
    }

    @Override
    public List<Retailer> viewRegistrationRequests() {
    	Session session = sf.openSession();

        List<Retailer> retailers = session.createQuery("FROM Retailer WHERE isActive = false", Retailer.class).list();
        session.close();
        return retailers;
    }
}
