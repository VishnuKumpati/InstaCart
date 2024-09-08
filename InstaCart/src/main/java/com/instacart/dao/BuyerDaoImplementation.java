package com.instacart.dao;

import com.instacart.entities.Buyer;

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
        Session session = sessionFactory.openSession();
        Query<Buyer> query = session.createQuery("FROM Buyer WHERE email = :email AND password = :password", Buyer.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.uniqueResult();
    }
}
