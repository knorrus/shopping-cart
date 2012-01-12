package com.logicify.shoppingcart.admin;

import com.logicify.shoppingcart.domain.Category;
import com.logicify.shoppingcart.domain.Product;
import com.logicify.shoppingcart.service.CategoryService;
import com.logicify.shoppingcart.service.ProductService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ru.perm.kefir.bbcode.BBProcessorFactory;
import ru.perm.kefir.bbcode.TextProcessor;

import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 12/6/11
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductInfo extends AbstractAdminPage {

    @SpringBean(required = true)
    private ProductService productService;
    @SpringBean(required = true)
    private CategoryService categoryService;

    private Long id;
    private Product product;
    private List<CategoryChecker> allCategoriesCheckers;

    private Form updateProductForm;
    private FeedbackPanel formFeedbackPanel;

    private static class CategoryChecker implements Serializable {
        private Category category;
        private boolean checked;

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public boolean getChecked() {
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

    public ProductInfo(PageParameters params) {
        super(params);
        product = null;
        if (params.getNamedKeys().contains("id")) {
            id = params.get("id").toLong();
            product = productService.getProductById(id);
        } else {
            //TODO: add link to error page;
        }
        formFeedbackPanel = new FeedbackPanel("feedback");
        add(formFeedbackPanel);

        updateProductForm = new Form("updateProductForm");
        add(updateProductForm);


        updateProductForm.add(new Label("productName", product.getName()));
        TextProcessor processor = BBProcessorFactory.getInstance().create();
        String formattedDescription = processor.process(product.getDescription());
        updateProductForm.add(new Label("productDescription", formattedDescription).setEscapeModelStrings(false));
        updateProductForm.add(new Label("productPrice", product.getPrice().toString()));

        List<Category> allCategoryList = categoryService.loadAllCategories();
        Set<Category> productCategorySet = product.getCategories();
        allCategoriesCheckers = new ArrayList<CategoryChecker>();

        ListIterator<Category> iterator = allCategoryList.listIterator();

        Category tempCategory;
        CategoryChecker tempCategoryChecker;
        while (iterator.hasNext()) {
            tempCategory = iterator.next();
            tempCategoryChecker = new CategoryChecker(tempCategory);
            if (productCategorySet.contains(tempCategory)) {
                tempCategoryChecker.check();
            }
            allCategoriesCheckers.add(tempCategoryChecker);
        }

        ListView<CategoryChecker> listView = new ListView<CategoryChecker>("categoryList", allCategoriesCheckers) {

            protected void populateItem(ListItem<CategoryChecker> item) {
                CategoryChecker categoryChecker = item.getModelObject();
                item.add(new Label("name", categoryChecker.getCategory().getName()));
                item.add(new CheckBox("check", new PropertyModel<Boolean>(categoryChecker, "checked")));
            }
        };
        updateProductForm.add(listView);

        updateProductForm.add(new Button("submitForm") {
            @Override
            public void onSubmit() {
                Set<Category> categorySet = new HashSet<Category>();
                ListIterator<CategoryChecker> iterator = allCategoriesCheckers.listIterator();
                CategoryChecker temp;
                while (iterator.hasNext()) {
                    temp = iterator.next();
                    if (temp.isChecked()) {
                        categorySet.add(temp.getCategory());
                    }
                }
                if (categorySet.isEmpty()) {
                    formFeedbackPanel.error("The product must contain at least one category");
                    return;
                }
                Product product = productService.getProductById(id);
                product.setCategories(categorySet);
                productService.updateProduct(product);
                setResponsePage(Index.class);
            }
        });
    }

}
