package com.logicify.shoppingcart.dao;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 11/22/11
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */

import com.logicify.shoppingcart.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    public void insertProduct(Product product) throws SQLException;

    public Product getProductById(Long id) throws SQLException;

    public List loadAllProducts() throws SQLException;
}
