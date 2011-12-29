package com.logicify.shoppingcart.dao;

import com.logicify.shoppingcart.domain.Category;
import com.logicify.shoppingcart.domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 11/23/11
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CategoryDao {

    public void insertCategory(Category category) throws SQLException;

    public void updateCategory (Category category) throws SQLException;

    public List loadAllCategories () throws SQLException;

    public Category getCategoryById (Long id) throws SQLException;
}
