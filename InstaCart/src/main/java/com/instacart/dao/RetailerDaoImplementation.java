package com.instacart.dao;

import com.instacart.entities.Retailer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class RetailerDaoImplementation implements RetailerDaoInterface {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private HttpServletRequest request;
    Session session;

    @Override
    public boolean existsByDetails(String email, Long contactNumber) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(r) FROM Retailer r WHERE r.email = :email OR r.contactNumber = :contactNumber";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("email", email);
            query.setParameter("contactNumber", contactNumber);
            Long count = query.uniqueResult();
            System.out.println("from retailer check" + count);
            return count > 0;
        }
    }

    @Override
    public void save(Retailer retailer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(retailer);
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Retailer findByEmailAndPassword(String email, String password) {
        Session session = sessionFactory.openSession();
        Query<Retailer> query = session.createQuery("FROM Retailer WHERE email = :email AND password = :password", Retailer.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.uniqueResult();
    }

    @Override
	public boolean passRecoverymail(String email) {
    	 session=sessionFactory.openSession();
    	 System.out.println("inside the retailer to check"+email);
    	String hql = "SELECT COUNT(*),userType,id FROM Retailer WHERE email = :email ";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("email", email);
        Object[] object = query.uniqueResult();
        if(object!=null && (Long) object[0] > 0) {
        	HttpSession session=request.getSession();
        	String userType=(String) object[1];
        	Long retailerId=(Long)object[2];
        	session.setAttribute("userType",userType);
        	session.setAttribute("userId",retailerId);
        	System.out.println(userType);
        	System.out.println(retailerId);
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

            Query<String>query = session.createQuery("UPDATE Retailer SET password = :password WHERE id = :userId",String.class);
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
