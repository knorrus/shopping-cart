package com.logicify.shoppingcart.dao.impl;

import com.logicify.shoppingcart.dao.ProductDao;
import com.logicify.shoppingcart.domain.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 11/22/11
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */

public class HibernateProductDao extends HibernateDaoSupport implements ProductDao {

    private static final Logger logger = LoggerFactory.getLogger(HibernateProductDao.class);

    public void insertProduct(Product product) {
        try {
            Session session = getSession();
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            logger.error("Error while insertion product", ex);
        }
    }

    public void updateProduct(Product product){
        try {
            Session session = getSession();
            session.beginTransaction();
            session.merge(product);
            session.getTransaction().commit();
        }
        catch (HibernateException ex){
            logger.error("Error while trying to update product", ex);
        }
    }


    public Product getProductById(Long id) {
        Product product = null;
        try {
            Session session = getSession();
            product = (Product) session.load(Product.class, id);
        } catch (HibernateException ex) {
            logger.error("Error while fetching product by id ", ex);
        }
        return product;
    }

    public List loadAllProducts() {
        List products = null;
        try {
            Session session = getSession();
            session.beginTransaction();
            products = session.createQuery("from Product").list();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            logger.error("Error while fetching all products ", ex);
        }
        return products;
    }
}


