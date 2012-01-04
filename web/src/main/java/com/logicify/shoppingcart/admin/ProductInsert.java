package com.logicify.shoppingcart.admin;

import com.logicify.shoppingcart.Index;
import com.logicify.shoppingcart.domain.Category;
import com.logicify.shoppingcart.domain.Product;
import com.logicify.shoppingcart.service.CategoryService;
import com.logicify.shoppingcart.service.ProductService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;
import java.util.*;

/**
 * Insert product page
 */
public class ProductInsert extends WebPage {

    @SpringBean(required = true)
    private ProductService productService;
    @SpringBean(required = true)
    private CategoryService categoryService;

    private Product product;
    private List<CategoryChecker> categoryCheckerList;

    private FeedbackPanel insertProductFeedbackPanel;
    private Form insertProductForm;
    private ListView<CategoryChecker> relatedCategoriesList;

    /**
     * Create product insert form component and save him to insertProductForm class member
     * Product insert form contain:
     * required TextField product name
     * required TextField product price
     * and TextArea with product description witch also required
     * NOTE: that to fill text area we are using WYSIWIG editor that's  why in text area presents bb-tags
     */
    public void createInsertProductForm() {
        insertProductForm = new Form("form");
        insertProductForm.setOutputMarkupId(true);
        createCategoriesList();
        insertProductForm.add(relatedCategoriesList);
        insertProductForm.add(new RequiredTextField("ProductName", new PropertyModel<Product>(this.product, "name")));
        insertProductForm.add(new RequiredTextField("ProductPrice", new PropertyModel<Product>(this.product, "price")));
        insertProductForm.add(new TextArea("ProductDescription", new PropertyModel<Product>(this.product, "description")));
        insertProductForm.add(new RequiredTextField("ProductTags"));
        insertProductForm.add(new Button("submitForm") {
            @Override
            public void onSubmit() {
                Set<Category> categoriesSet = new HashSet<Category>();
                ListIterator<CategoryChecker> categoryCheckerIterator = categoryCheckerList.listIterator();
                while (categoryCheckerIterator.hasNext()) {
                    CategoryChecker next = categoryCheckerIterator.next();
                    if (next.isChecked()) {
                        categoriesSet.add(next.getCategory());
                    }
                }
                product.setCategories(categoriesSet);
                product.setDate(new Date());
                productService.insertProduct(product);
                setResponsePage(Index.class);
            }
        });
    }

    /**
     * Create list of existing categories component and save it to the relatedCategoriesList class member
     * list consist of a checkboxes, and to represent the category relation we are using CategoryChecker class
     */
    public void createCategoriesList(){
        List<Category> categories = this.categoryService.loadAllCategories();
        ListIterator<Category> categoryListIterator = categories.listIterator();
        while (categoryListIterator.hasNext()) {
            this.categoryCheckerList.add(new CategoryChecker(categoryListIterator.next()));
        }
        relatedCategoriesList = new ListView<CategoryChecker>("categoryList", this.categoryCheckerList) {
            protected void populateItem(ListItem<CategoryChecker> item) {
                CategoryChecker categoryWrapper = item.getModelObject();
                item.add(new Label("name", categoryWrapper.getCategory().getName()));
                item.add(new CheckBox("check", new PropertyModel(categoryWrapper, "checked")));
            }
        };
        relatedCategoriesList.setReuseItems(true);
    }

    public ProductInsert() {
        this.product = new Product();
        this.categoryCheckerList = new ArrayList<CategoryChecker>();
        this.insertProductFeedbackPanel = new FeedbackPanel("feedback");

        createInsertProductForm();

        add(insertProductFeedbackPanel);
        add(insertProductForm);
    }

    /**
     * CategoryChecker class need for:
     * creating the list of the checkboxes witch represent all existing categories
     */
    private static class CategoryChecker implements Serializable{
        private Category category;
        private boolean checked;

        public void setChecked(boolean checked){
            this.checked = true;
        }

        public boolean getChecked(){
            return this.checked;
        }

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