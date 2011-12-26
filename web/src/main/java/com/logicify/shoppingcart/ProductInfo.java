package com.logicify.shoppingcart;

import com.logicify.shoppingcart.domain.Category;
import com.logicify.shoppingcart.domain.Product;
import com.logicify.shoppingcart.service.ProductService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ru.perm.kefir.bbcode.BBProcessorFactory;
import ru.perm.kefir.bbcode.TextProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 12/6/11
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductInfo extends WebPage {

    @SpringBean(required = true)
    private ProductService service;

    public ProductInfo(PageParameters params) {
        super(params);
        Product product = null;
        if (params.getNamedKeys().contains("id")) {
            Long id = params.get("id").toLong();
            product = service.getProductById(id);
        }

        TextProcessor processor = BBProcessorFactory.getInstance().create();
        String formattedDescription = processor.process(product.getDescription());

        add(new Label("productName", product.getName()));
        add(new Label("productDescription", formattedDescription).setEscapeModelStrings(false));
        add(new Label("productPrice", product.getPrice().toString()));

        ListView<Category> listview = new ListView<Category>("categoryList", this.getProductsModel(product)) {

            protected void populateItem(ListItem<Category> item) {
                Category category = item.getModelObject();
                BookmarkablePageLink<Category> linkToCategory = new BookmarkablePageLink<Category>("linkToCategory", CategoryInfo.class);
                linkToCategory.getPageParameters().set("id", category.getId());
                linkToCategory.add(new Label("linksText", category.getName()));
                item.add(linkToCategory);
            }
        };
        add(listview);
    }

    private IModel<List<Category>> getProductsModel(Product product) {
        return new PropertyModel<List<Category>>(product, "categories") {
            public List<Category> getObject() {
                return new ArrayList<Category>((Set<Category>) super.getObject());
            }
        };
    }

}
