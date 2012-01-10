package com.logicify.shoppingcart.admin;

import com.logicify.shoppingcart.Index;
import com.logicify.shoppingcart.components.converters.KeywordConverter;
import com.logicify.shoppingcart.domain.Category;
import com.logicify.shoppingcart.domain.Keyword;
import com.logicify.shoppingcart.domain.Product;
import com.logicify.shoppingcart.service.CategoryService;
import com.logicify.shoppingcart.service.ProductService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.*;

public class ProductInsert extends WebPage {

    @SpringBean(required = true)
    private ProductService productService;
    @SpringBean(required = true)
    private CategoryService categoryService;

    private List<CategoryChecker> categoryCheckerList;

    private Set<Keyword> productKeywordsSet;

    public ProductInsert() {
        add(new ProductForm("form"));
        add(new FeedbackPanel("feedback"));
    }

    public final class RelatedCategoriesListView extends ListView<CategoryChecker> {

        public RelatedCategoriesListView(final String id, List<CategoryChecker> list) {
            super(id, list);
        }

        @Override
        protected void populateItem(ListItem<CategoryChecker> item) {
            CategoryChecker categoryWrapper = item.getModelObject();
            item.add(new Label("name", categoryWrapper.getCategory().getName()));
            item.add(new CheckBox("check", new PropertyModel(categoryWrapper, "checked")));
        }
    }

    public final class ProductForm extends Form<Product> {

        public ProductForm(final String id) {
            super(id, new CompoundPropertyModel<Product>(new Product()));
            Product product = getModelObject();
            add(new RequiredTextField<String>("ProductName", new PropertyModel<String>(product, "name"), String.class)
                    .add(StringValidator.LengthBetweenValidator.lengthBetween(3, 35))
                    .add(new PatternValidator("[a-zA-Z0-9\\,\\+\\.\\s\\-\\_]{1,}")));

            add(new TextField<String>("ProductTags", new PropertyModel(product, "keywords"), String.class) {
                @Override
                public <C> IConverter<C> getConverter(Class<C> type) {
                    return new KeywordConverter();
                }
            }
                    .add(StringValidator.LengthBetweenValidator.lengthBetween(3, 100))
                    .add(new PatternValidator("[a-zA-Z0-9\\,\\+\\.\\s\\-\\_]{1,}")));

            add(new RequiredTextField<BigDecimal>("ProductPrice", new PropertyModel<BigDecimal>(product, "price"), BigDecimal.class));

            add(new TextArea<String>("ProductDescription", new PropertyModel<String>(product, "description"))
                    .setType(String.class)
                    .setRequired(true)
                    .add(StringValidator.lengthBetween(25, 700))
            );

            categoryCheckerList = new ArrayList<CategoryChecker>();
            List<Category> categories = categoryService.loadAllCategories();
            ListIterator<Category> categoryListIterator = categories.listIterator();
            while (categoryListIterator.hasNext()) {
                categoryCheckerList.add(new CategoryChecker(categoryListIterator.next()));
            }
            add(new RelatedCategoriesListView("categoryList", categoryCheckerList).setReuseItems(true));
            setMarkupId("form");
        }

        @Override
        public void onSubmit() {
            Product product = getModelObject();
            product.setDate(new Date());
            Set<Category> categoriesSet = new HashSet<Category>();
            ListIterator<CategoryChecker> categoryCheckerIterator = categoryCheckerList.listIterator();
            while (categoryCheckerIterator.hasNext()) {
                CategoryChecker next = categoryCheckerIterator.next();
                if (next.isChecked()) {
                    categoriesSet.add(next.getCategory());
                }
            }
            product.setCategories(categoriesSet);
            productService.insertProduct(product);
            setResponsePage(Index.class);
        }
    }

    /**
     * CategoryChecker class need for:
     * creating the list of the checkboxes witch represent all existing categories
     */
    private static class CategoryChecker implements Serializable {
        private Category category;
        private boolean checked;

        public void setChecked(boolean checked) {
            this.checked = true;
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
}