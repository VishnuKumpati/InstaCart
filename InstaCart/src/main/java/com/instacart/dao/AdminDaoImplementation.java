package com.instacart.dao;

import com.instacart.entities.Admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDaoImplementation implements AdminDaoInterface {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private HttpServletRequest request;
    Session session=null;

    @Override
    public boolean existsByDetails(String email, Long contactno) {
     
        session = sessionFactory.openSession();
        try {
            String hql = "SELECT COUNT(*) FROM Admin WHERE email = :email OR contactNumber = :contactno";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("email", email);
            query.setParameter("contactno", contactno);
            Long count = query.uniqueResult();
            System.out.println("from admin check"+count);
            return count > 0;
        } finally {
            session.close();
        }
    }

    @Override
    public void save(Admin admin) {
       session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(admin);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Admin findByEmailAndPassword(String email, String password) {
       session = sessionFactory.openSession();
        Query<Admin> query = session.createQuery("FROM Admin WHERE email = :email AND password = :password", Admin.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.uniqueResult();
    }

    @Override
    public boolean passRecoverymail(String email) {
        session = sessionFactory.openSession();
        System.out.println("inside the admin to check"+email);
        String hql = "SELECT COUNT(*), userType, id FROM Admin WHERE email = :email";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("email", email);
        Object[] object = query.uniqueResult();

        if (object != null && (Long) object[0] > 0) {  // Check if the count is greater than 0
            HttpSession session = request.getSession();
            String userType = (String) object[1];
            Long adminId = (Long) object[2];
            session.setAttribute("userType", userType);
            session.setAttribute("userId", adminId);
            System.out.println(userType);
            System.out.println(adminId);
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

            Query<String>query = session.createQuery("UPDATE Admin SET password = :password WHERE id = :userId",String.class);
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
