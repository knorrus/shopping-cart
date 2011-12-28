package com.logicify.shoppingcart;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 11/28/11
 * Time: 7:05 PM
 * To change this template use File | Settings | File Templates.
 */
import com.logicify.shoppingcart.admin.CategoryInfo;
import com.logicify.shoppingcart.admin.ProductInfo;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;


public class ShoppingCartApplication extends WebApplication {

    public ShoppingCartApplication() {
    }

    @Override
    public void init(){
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        mountPage("/shopping-cart/product", ProductInfo.class);
        mountPage("/shopping-cart/category", CategoryInfo.class);
    }

    @Override
    public Class getHomePage() {
        return Index.class;
    }
}