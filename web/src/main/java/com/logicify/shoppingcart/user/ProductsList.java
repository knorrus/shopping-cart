package com.logicify.shoppingcart.user;

import com.logicify.shoppingcart.domain.Product;
import com.logicify.shoppingcart.service.ProductService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class ProductsList extends WebPage {

    @SpringBean(required = true)
    private ProductService service;

    public ProductsList() {
        List products = this.service.loadAllProduct();
        ListView<Product> listview = new ListView<Product>("productList", products) {
            protected void populateItem(ListItem<Product> item) {
                Product product = item.getModelObject();
                BookmarkablePageLink<Product> linkToProduct = new BookmarkablePageLink<Product>("linkToProduct", ProductDetails.class);
                linkToProduct.getPageParameters().set("id", product.getId());
                linkToProduct.add(new Label("linksText", product.getName()));
                item.add(linkToProduct);
            }
        };
        add(listview);
    }
}