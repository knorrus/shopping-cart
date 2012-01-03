package com.logicify.shoppingcart.admin;

import com.logicify.shoppingcart.domain.Product;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

/**
 * Created by IntelliJ IDEA.
 * User: knorr
 * Date: 12/30/11
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
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

        Link insertProduct = new Link("linkToInsertProductPage") {
            @Override
            public void onClick() {
                setResponsePage(new ProductInsert());
            }
        };
        insertProduct.add(new Label("linksText", "Insert new product"));
        add(insertProduct);

        Link insertCategory = new Link("linkToInsertCategoryPage") {
            @Override
            public void onClick() {
                setResponsePage(new CategoryInsert());
            }
        };
        insertCategory.add(new Label("linksText", "Insert new Category"));
        add(insertCategory);

        Link findProduct =new Link("linkToFindProductPage") {
            @Override
            public void onClick() {
                setResponsePage(new ProductSearch());
            }
        };
        findProduct.add(new Label("linksText", "Find product"));
        add(findProduct);
    }
}
