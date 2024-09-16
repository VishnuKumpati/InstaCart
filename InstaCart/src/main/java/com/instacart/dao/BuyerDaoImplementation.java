package com.instacart.dao;

import com.instacart.entities.Buyer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.query.Query;

@Repository
public class BuyerDaoImplementation implements BuyerDaoInterface {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HttpServletRequest request; 

    @Override
    public boolean existsByDetails(String email, Long contactNumber) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(b) FROM Buyer b WHERE b.email = :email OR b.contactNumber = :contactNumber";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("email", email);
            query.setParameter("contactNumber", contactNumber);
            Long count = query.uniqueResult();
            System.out.println("from buyer check" + count);
            return count > 0;
        }
    }

    @Override
    public void save(Buyer buyer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.persist(buyer);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    @Override
    public Buyer findByEmailAndPassword(String email, String password) {
        Session  session = sessionFactory.openSession();
        Query<Buyer> query = session.createQuery("FROM Buyer WHERE email = :email AND password = :password", Buyer.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.uniqueResult();
    }

    @Override
	public boolean passRecoverymail(String email) {
	 Session session=sessionFactory.openSession();
	System.out.println("inside the buyer to check"+email);
	String hql = "SELECT COUNT(*),userType,id FROM Buyer WHERE email = :email ";
    Query<Object[]> query = session.createQuery(hql, Object[].class);
    query.setParameter("email", email);
    Object[] object = query.uniqueResult();
       System.out.println("object:  "+object[0]);
       System.out.println("object:  "+object[1]);
       System.out.println("object:  "+object[2]);
    if(object!=null && (Long) object[0] > 0) {
    	HttpSession sessionHttp=request.getSession();
    	String userType=(String) object[1];
    	Long buyerId=(Long)object[2];
    	sessionHttp.setAttribute("userType",userType);
    	sessionHttp.setAttribute("userId",buyerId);
    	System.out.println(userType);
    	System.out.println(buyerId);
    	return true;
    }
    return false;
    
	}

    @Override
    public String updatePassword(String password, Long userId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            // Begin the transaction manually
            transaction = session.beginTransaction();
            
            System.out.println("Trying to update password");
            System.out.println(password);
            System.out.println(userId);

            Query<String>query = session.createQuery("UPDATE Buyer SET password = :password WHERE id = :userId",String.class);
            query.setParameter("password", password);
            query.setParameter("userId", userId);
            
            int result = query.executeUpdate();  

            transaction.commit();
            
            return result > 0 ? "success" : "failure";
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            return "failure";
        }
    }


}
