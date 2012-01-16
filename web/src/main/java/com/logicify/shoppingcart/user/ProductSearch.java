package com.logicify.shoppingcart.user;

import com.logicify.shoppingcart.domain.Category;
import com.logicify.shoppingcart.domain.Product;
import com.logicify.shoppingcart.service.CategoryService;
import com.logicify.shoppingcart.service.ProductService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import javax.persistence.metamodel.ListAttribute;
import java.io.Serializable;
import java.util.*;


public class ProductSearch extends AbstractUserPage {

    @SpringBean(required = true)
    private ProductService productService;
    @SpringBean(required = true)
    private CategoryService categoryService;

    private ProductSearchPanel results;

    public ProductSearch() {
        add(new FeedbackPanel("feedback"));
        add(new SearchProductForm("searchForm"));
        results = new ProductSearchPanel("panel");
        add(results);
    }

    public final class SearchProductForm extends Form<ValueMap> {
        private String sortOrder = "asc";
        private List<String> orderChoices = Arrays.asList(new String[]{"asc", "desc"});
        private String sortCriteria = "name";
        private List<String> criteriaChoices = Arrays.asList(new String[]{"name", "price"});

        private List<CategoryChecker> categoryCheckers;      //wrappers on the categories
        private List<Category> categories;      // all existing categories
        private Category metaCategory; //meta category - "all" product filter

        public SearchProductForm(final String id) {
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));

            add(new RequiredTextField<String>("keywordPattern", String.class)
                    .add(StringValidator.lengthBetween(3, 25))
                    .add(new PatternValidator("[a-zA-Z0-9\\,\\+\\.\\s\\-\\_]{1,}"))
            );

            //Select components for sort order
            add(new DropDownChoice("sortOrder", new PropertyModel(this, "sortOrder"), orderChoices)
                    .setRequired(true));
            add(new DropDownChoice("sortCriteria", new PropertyModel(this, "sortCriteria"), criteriaChoices)
                    .setRequired(true));

            //Create category filter list view
            categories = categoryService.loadAllCategories();
            categoryCheckers = new ArrayList<CategoryChecker>();
            metaCategory = new Category();
            metaCategory.setName("all");
            categoryCheckers.add(new CategoryChecker(metaCategory));
            if (!categories.isEmpty()) {
                Iterator<Category> iterator = categories.iterator();
                while (iterator.hasNext()) {
                    categoryCheckers.add(new CategoryChecker(iterator.next()));
                }
            }
            add(new RelatedCategoriesListView("categoryList", categoryCheckers).setReuseItems(true));
        }

        @Override
        protected void onSubmit() {
            ValueMap values = getModelObject();
            String keyPattern = values.getString("keywordPattern");
            List<Product> products;

            //get category filters result
            List<Category> allCategories = new ArrayList<Category>();
            for (CategoryChecker categoryChecker : categoryCheckers) {
                if (categoryChecker.isChecked()) {
                    allCategories.add(categoryChecker.getCategory());
                }
            }

            if (allCategories.isEmpty() || categoryCheckers.get(0).isChecked()) {
                //no filters or "all" categories
                products = productService.findProductsByTag(keyPattern);
                for (CategoryChecker checker : categoryCheckers) {
                    if (!checker.getCategory().getName().equals("all")) {
                        checker.unCheck();
                    }
                }
            } else {
                //search by filter list
                products = productService.findProductsByTagFilteredByCategories(keyPattern, allCategories);
            }
            //Chose necessary panel
            Fragment responsePanel;
            if (products.isEmpty()) {
                responsePanel = results.createNoResultListFragment("No products found");
            } else {
                responsePanel = results.createResultListFragment(products, sortOrder, sortCriteria);
            }
            results.setFragment(responsePanel);
        }
    }

    /**
     * RelatedCategoriesListView - list of checkboxes need for representing of the existing categories
     */
    public final class RelatedCategoriesListView extends ListView<CategoryChecker> {

        public RelatedCategoriesListView(final String id, List<CategoryChecker> list) {
            super(id, list);
        }

        @Override
        protected void populateItem(ListItem<CategoryChecker> item) {
            CategoryChecker categoryWrapper = item.getModelObject();
            item.add(new Label("name", categoryWrapper.getCategory().getName()));
            item.add(new CheckBox("check", new PropertyModel<Boolean>(categoryWrapper, "checked")));
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
}
