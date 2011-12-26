package com.logicify.shoppingcart;

import com.logicify.shoppingcart.domain.Category;
import com.logicify.shoppingcart.domain.Product;
import com.logicify.shoppingcart.service.CategoryService;
import com.logicify.shoppingcart.service.ProductService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: knorr
 * Date: 12/17/11
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class InsertProduct extends WebPage {


    @SpringBean(required = true)
    private ProductService productService;
    @SpringBean(required = true)
    private CategoryService categoryService;

    private Product product;
    private List<CategoryChecker> categoryCheckerList;

    public InsertProduct() {
        this.product = new Product();
        this.categoryCheckerList = new ArrayList<CategoryChecker>();

        List<Category> categories = this.categoryService.loadAllCategories();
        ListIterator<Category> categoryListIterator = categories.listIterator();

        while (categoryListIterator.hasNext()) {
            this.categoryCheckerList.add(new CategoryChecker(categoryListIterator.next()));
        }
        ListView<CategoryChecker> listView = new ListView<CategoryChecker>("categoryList", this.categoryCheckerList) {
            protected void populateItem(ListItem<CategoryChecker> item) {
                CategoryChecker categoryWrapper = item.getModelObject();
                item.add(new Label("name", categoryWrapper.getCategory().getName()));
                item.add(new CheckBox("check", new PropertyModel(categoryWrapper, "checked")));
            }
        };
        listView.setReuseItems(true);

        Form form = new Form("form");
        add(form);
        form.setOutputMarkupId(true);
        form.add(listView);
        form.add(new TextField("ProductName", new PropertyModel<Product>(this.product, "name")));
        form.add(new TextField("ProductPrice", new PropertyModel<Product>(this.product, "price")));
        form.add(new TextArea("ProductDescription", new PropertyModel<Product>(this.product, "description")));
        form.add(new Button("submitForm") {
            @Override
            public void onSubmit() {

                Set<Category> categoriesSet = new HashSet<Category>();
                ListIterator<CategoryChecker> categoryCheckerIterator = categoryCheckerList.listIterator();
                while (categoryCheckerIterator.hasNext()) {
                    if (categoryCheckerIterator.next().isChecked()) {
                        categoriesSet.add(categoryCheckerIterator.next().getCategory());
                    }
                }

                product.setCategories(categoriesSet);
                product.setDate(new Date());
                productService.insertProduct(product);

                setResponsePage(Index.class);
            }
        });
    }

    private static class CategoryChecker implements Serializable{
        private Category category;
        private boolean checked;

        public CategoryChecker(Category category) {
            this.setCategory(category);
            this.unCheck();
        }

        public Category getCategory() {
            return this.category;
        }

        private void setCategory(Category category) {
            this.category = category;
        }

        public boolean isChecked() {
            return this.checked;
        }

        public void check() {
            this.checked = true;
        }

        public void unCheck() {
            this.checked = false;
        }
    }
}