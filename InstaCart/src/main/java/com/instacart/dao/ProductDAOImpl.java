package com.instacart.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.instacart.entities.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Product> findByCategory(String categoryName) {
        Session session = sessionFactory.openSession();
        Query<Product> query = session.createQuery(
            "SELECT p FROM Product p WHERE p.category.name = :categoryName", Product.class);
        query.setParameter("categoryName", categoryName);
        List<Product> products = query.getResultList();
        session.close(); // Don't forget to close the session
        return products;
    }
}
