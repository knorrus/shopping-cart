package com.logicify.shoppingcart.user;

import com.logicify.shoppingcart.domain.Category;
import com.logicify.shoppingcart.domain.Product;
import com.logicify.shoppingcart.service.CategoryService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 12/12/11
 * Time: 6:05 PM
 * To change this template use File | Settings | File Templates.
 * public class ProductInfo extends WebPage{
 */

public class CategoryDetails extends AbstractUserPage {
    @SpringBean(required = true)
    private CategoryService service;

    public CategoryDetails(PageParameters params) {

        super(params);
        Category category = null;
        if (params.getNamedKeys().contains("id")) {
            Long id = params.get("id").toLong();
            category = service.getCategoryById(id);
        }
        add(new Label("categoryName", category.getName()));
        add(new Label("categoryDescription", category.getDescription()));

        ListView<Product> listview = new ListView<Product>("productList", this.getProductsModel(category)) {

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

    private IModel<List<Product>> getProductsModel(Category category) {
        return new PropertyModel<List<Product>> (category, "products") {
            public List<Product> getObject() {
                return new ArrayList<Product>((Set<Product>) super.getObject());
            }
        };
    }


}

