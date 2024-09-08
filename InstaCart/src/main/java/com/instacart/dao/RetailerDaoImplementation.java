package com.instacart.dao;

import com.instacart.entities.Retailer;
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
}
