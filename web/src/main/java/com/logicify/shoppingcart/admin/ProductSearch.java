package com.logicify.shoppingcart.admin;

import com.logicify.shoppingcart.domain.Product;
import com.logicify.shoppingcart.service.ProductService;
import org.apache.commons.collections.ListUtils;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: knorr
 * Date: 1/3/12
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductSearch extends WebPage {

    @SpringBean(required = true)
    private ProductService productService;

    private ListView<Product> productSearchResultListView;
    private Form searchForm;
    private Label searchErrMessage;

    private String name;

    public ProductSearch() {

        productSearchResultListView = new ListView<Product>("productList", new ArrayList<Product>()) {
            @Override
            protected void populateItem(ListItem<Product> productListItem) {
                Product product = productListItem.getModelObject();
                BookmarkablePageLink<Product> linkToProduct = new BookmarkablePageLink<Product>("linkToProduct", ProductInfo.class);
                linkToProduct.getPageParameters().set("id", product.getId());
                linkToProduct.add(new Label("linksText", product.getName()));
                productListItem.add(linkToProduct);
            }
        };
        add(productSearchResultListView);

        searchErrMessage = new Label("errMsg", "No results");
        searchErrMessage.setVisible(false);
        add(searchErrMessage);

        searchForm = new Form("searchForm");
        add(searchForm);
        searchForm.add(new RequiredTextField("ProductName", new Model<String>(name) {
            @Override
            public String getObject() {
                return name;
            }

            @Override
            public void setObject(String object) {
                name = object;
            }
        }));
        searchForm.add(new Button("Find") {
            @Override
            public void onSubmit() {
                List products = productService.findProductsByName(name);
                if (products.isEmpty()) {
                    searchErrMessage.setVisible(true);
                    productSearchResultListView.getList().clear();
                } else {
                    productSearchResultListView.setList(products);
                    searchErrMessage.setVisible(false);
                }
            }
        });
    }
}
