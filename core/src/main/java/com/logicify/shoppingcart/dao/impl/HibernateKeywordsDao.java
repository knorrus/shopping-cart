package com.logicify.shoppingcart.dao.impl;

import com.logicify.shoppingcart.dao.KeywordsDao;
import com.logicify.shoppingcart.domain.Keyword;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateKeywordsDao extends HibernateDaoSupport implements KeywordsDao {

    private static final Logger logger = LoggerFactory.getLogger(HibernateKeywordsDao.class);

    public void insertKeyword(Keyword keyword) {
        try {
            Session session = getSession();
            session.beginTransaction();
            session.save(keyword);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            logger.error("Error while trying to insert keyword ", ex);
        }
    }

}
