package com.logicify.shoppingcart.service;

import com.logicify.shoppingcart.dao.ProductDao;
import com.logicify.shoppingcart.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 11/25/11
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductService {

    @Autowired
    private ProductDao productDao;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public void insertProduct (Product product) {
        try{
            productDao.insertProduct(product);
        }
        catch (SQLException ex){
            logger.error("Error while trying to insert ne product", ex);
        }
    }

    public List loadAllProduct () {
        List products = null;
        try{
            products = productDao.loadAllProducts();
        }
        catch (SQLException ex) {
            logger.error("Error", ex);
        }
        return products;
    }

    public Product getProductById (Long id) {
        Product product = null;
        try{
            product = productDao.getProductById(id);
        }
        catch (SQLException ex){
            logger.error("Error", ex);
        }
        return product;
    }
}
