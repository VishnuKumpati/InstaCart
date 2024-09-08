package com.instacart.dao;

import com.instacart.entities.Admin;
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

    @Override
    public boolean existsByDetails(String email, Long contactno) {
     
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
        Query<Admin> query = session.createQuery("FROM Admin WHERE email = :email AND password = :password", Admin.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.uniqueResult();
    }
}
