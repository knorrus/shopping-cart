package com.logicify.shoppingcart.admin;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 11/28/11
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */

import com.logicify.shoppingcart.domain.Product;
import com.logicify.shoppingcart.service.ProductService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class ProductsList extends AbstractAdminPage {

    @SpringBean(required = true)
    private ProductService service;

    public ProductsList() {
        List products = this.service.loadAllProduct();
        ListView<Product> listview = new ListView<Product>("productList", products) {
            protected void populateItem(ListItem<Product> item) {
                Product product = item.getModelObject();
                BookmarkablePageLink<Product> linkToProduct = new BookmarkablePageLink<Product>("linkToProduct", ProductInfo.class);
                linkToProduct.getPageParameters().set("id", product.getId());
                linkToProduct.add(new Label("linksText", product.getName()));
                item.add(linkToProduct);
            }
        };
        add(listview);
    }
}

