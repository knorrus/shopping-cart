package com.logicify.shoppingcart;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 11/28/11
 * Time: 7:05 PM
 * To change this template use File | Settings | File Templates.
 */
import com.logicify.shoppingcart.admin.*;
import com.logicify.shoppingcart.components.converters.ShoppingCartConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;


public class ShoppingCartApplication extends WebApplication {

    public ShoppingCartApplication() {
    }



    @Override
    public void init(){
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        //admin module
        mountPage("/shopping-cart/admin/products/", ProductsList.class);
        mountPage("/shopping-cart/admin/categories/", CategoriesList.class);
        mountPage("/shopping-cart/admin/product/", ProductInfo.class);
        mountPage("/shopping-cart/admin/category/", CategoryInfo.class);
        mountPage("/shopping-cart/admin/search/", ProductSearch.class);

    }

    @Override
    protected IConverterLocator newConverterLocator() {
        return new ShoppingCartConverterLocator();
    }

    @Override
    public Class getHomePage() {
        return Index.class;
    }
}