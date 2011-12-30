package com.logicify.shoppingcart.admin;

import com.logicify.shoppingcart.Index;
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
 * Date: 12/12/11
 * Time: 6:05 PM
 * To change this template use File | Settings | File Templates.
 * public class ProductInfo extends WebPage{
 */

public class CategoryInfo extends WebPage {
    @SpringBean(required = true)
    private CategoryService categoryService;

    @SpringBean(required = true)
    private ProductService productService;

    private Long id;
    private ListView<ProductChecker> listView;

    /**
     * ProductChecker - used for generate list of products which present in category
     * if checked = true then user set flag and product must be added to category
     */
    private static class ProductChecker implements Serializable {
        private Product product;
        private Boolean checked;

        public ProductChecker(Product product) {
            this.setProduct(product);
            this.unCheck();
        }

        public void setChecked(boolean checked){
            this.checked = checked;
        }

        public boolean getChecked(){
            return this.checked;
        }

        private void setProduct (Product product){
            this.product = product;
        }

        public Product getProduct (){
            return this.product;
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

    /**
     * @param params
     * Category details page (for admin interface). Page contain category name, category description
     * and a list of flags that represent the products presented in the category
     * admin user can add/remove product to/from category by checking needed flags
     */
    public CategoryInfo(PageParameters params) {
        super(params);
        Category category = null;
        if (params.getNamedKeys().contains("id")) {
            id = params.get("id").toLong();
            category = categoryService.getCategoryById(id);
        }
        else {
            //TODO: add link to the error page
        }

        Form updateCategoryForm = new Form("updateCategoryForm");

        add(updateCategoryForm);
        updateCategoryForm.add(new Label("categoryName", category.getName()));

        TextProcessor processor = BBProcessorFactory.getInstance().create();
        String formattedDescription = processor.process(category.getDescription());
        updateCategoryForm.add(new Label("categoryDescription", formattedDescription).setEscapeModelStrings(false));

        
        List<Product> allProducts = productService.loadAllProduct();
        Set<Product>  categoryProducts = category.getProducts();
        List<ProductChecker> allProductsCheckers = new ArrayList<ProductChecker>();

        ListIterator<Product> iterator = allProducts.listIterator();
        Product tempProduct;
        ProductChecker tempProductChecker;
        while (iterator.hasNext()){
            tempProduct = iterator.next();
            tempProductChecker = new ProductChecker(tempProduct);
            if (categoryProducts.contains(tempProduct)){
                tempProductChecker.check();
            }
            allProductsCheckers.add(tempProductChecker);
        }
            listView = new ListView<ProductChecker>("productList", allProductsCheckers) {
            protected void populateItem(ListItem<ProductChecker> item) {
                ProductChecker productWrapper = item.getModelObject();
                item.add(new Label("name", productWrapper.getProduct().getName()));
                CheckBox checkBox = new CheckBox("check", new PropertyModel<Boolean>(productWrapper, "checked"));
                item.add(checkBox);
            }
        };

        updateCategoryForm.add(listView);

        updateCategoryForm.add(new Button("submitForm") {
            @Override
            public void onSubmit() {
                Set<Product> productsSet = new HashSet<Product>();
                List list = listView.getList();
                ListIterator<ProductChecker> productCheckerIterator = list.listIterator();
                while (productCheckerIterator.hasNext()) {
                    ProductChecker next = productCheckerIterator.next();
                    if (next.isChecked()) {
                        productsSet.add(next.getProduct());
                    }
                }
                Category category = categoryService.getCategoryById(id);
                category.setProducts(productsSet);
                categoryService.updateCategory(category);
                setResponsePage(Index.class);
            }
        });

    }

}
