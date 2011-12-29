package com.logicify.shoppingcart.service;

import com.logicify.shoppingcart.dao.CategoryDao;
import com.logicify.shoppingcart.domain.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 12/12/11
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public List loadAllCategories () {
        List categories = null;
        try{
            categories = categoryDao.loadAllCategories();
        }
        catch (SQLException ex) {
            logger.error("Error", ex);
        }
        return categories;
    }

    public Category getCategoryById (Long id){
        Category category = null;
        try{
            category = categoryDao.getCategoryById(id);
        }
        catch (SQLException ex){
            logger.error("Error", ex);
        }
        return category;
    }

    public void updateCategory (Category category){
        try{
            categoryDao.updateCategory(category);
        }
        catch (SQLException ex){
            logger.error("Can't update category "+category.getName(),ex);
        }
    }


}
