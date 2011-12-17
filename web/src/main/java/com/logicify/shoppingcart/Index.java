package com.logicify.shoppingcart;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 12/6/11
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Index extends WebPage {
    public Index() {
        Link productList = new Link("linkToProductsListPage") {
            @Override
            public void onClick() {
                setResponsePage(new ListOfExistingProducts());
            }
        };
        productList.add(new Label("linksText", "List of Existing products"));
        add(productList);

        Link categoryList = new Link("linkToCategoriesListPage"){
            @Override
            public void onClick() {
                setResponsePage(new ListOfExistingCategories());
            }
        };
        categoryList.add(new Label("linksText", "List of Existing categories"));
        add(categoryList);

        Link insertProduct = new Link("linkToInsertProductPage") {
            @Override
            public void onClick() {
                setResponsePage(new InsertProduct());
            }
        };
        insertProduct.add(new Label("linksText", "Insert new product"));
        add(insertProduct);
    }
}
