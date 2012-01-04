package com.logicify.shoppingcart.user;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

public class Index extends WebPage {
    public Index() {

        Link productList = new Link("linkToProductsListPage") {
            @Override
            public void onClick() {
                setResponsePage(new ProductsList());
            }
        };
        productList.add(new Label("linksText", "List of Existing products"));
        add(productList);


        Link categoryList = new Link("linkToCategoriesListPage"){
            @Override
            public void onClick() {
                setResponsePage(new CategoriesList());
            }
        };
        categoryList.add(new Label("linksText", "List of Existing categories"));
        add(categoryList);
    }
}
