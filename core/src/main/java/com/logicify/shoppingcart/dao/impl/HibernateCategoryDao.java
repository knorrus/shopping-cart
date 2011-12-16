package com.logicify.shoppingcart.dao.impl;

import com.logicify.shoppingcart.dao.CategoryDao;
import com.logicify.shoppingcart.domain.Category;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 11/23/11
 * Time: 10:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateCategoryDao extends HibernateDaoSupport implements CategoryDao {

    private static final Logger logger = LoggerFactory.getLogger(HibernateCategoryDao.class);

    public void insertCategory(Category category) {
        try{
            Session session = getSession();
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            logger.error("Error while inserting category");
        }
    }

    public List loadAllCategories () {
        List categories = null;
        try {
            Session session = getSession();
            session.beginTransaction();
            categories = session.createQuery("from Category ").list();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            logger.error("Error while fetching all categories ", ex);
        }
        return categories;
    }

    public Category getCategoryById (Long id){
        Category category = null;
        try{
            Session session = getSession();
            category = (Category) session.load(Category.class, id);
        } catch (HibernateException ex){
            logger.error("Error while fetching all category " + id, ex);
        }
        return category;
    }

}
