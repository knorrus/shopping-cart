package com.logicify.shoppingcart.service;

import com.logicify.shoppingcart.dao.KeywordsDao;
import com.logicify.shoppingcart.domain.Keyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: knorr
 * Date: 1/11/12
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class KeywordsService {
    @Autowired
    KeywordsDao dao;

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public void insertKeywords(Collection<Keyword> collection){
        try{
            dao.insertKeywords(collection);
        }
        catch (SQLException ex){
            logger.error("Can't insert keyword collection", ex);
        }
    }

}
