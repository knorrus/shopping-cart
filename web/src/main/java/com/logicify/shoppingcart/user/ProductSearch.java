package com.logicify.shoppingcart.user;

import com.logicify.shoppingcart.domain.Product;
import com.logicify.shoppingcart.service.ProductService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import java.util.List;


public class ProductSearch extends WebPage{

    @SpringBean(required = true)
    private ProductService productService;
    private ProductSearchPanel results;



    public final class SearchProductForm extends Form<ValueMap>{

        public SearchProductForm (final String id){
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
            add(new RequiredTextField<String>("keywordPattern",String.class)
                    .add(StringValidator.lengthBetween(3,25))
                    .add(new PatternValidator("[a-zA-Z0-9\\,\\+\\.\\s\\-\\_]{1,}"))
            );

        }

        @Override
        protected void onSubmit() {
            ValueMap values = getModelObject();
            String keyPattern = values.getString("keywordPattern");
            List<Product> products = productService.findProductsByTag(keyPattern);
            Fragment responsePanel;
            if (products.isEmpty()){
                responsePanel = results.createNoResultListFragment("No products found");
            }
            else {
                responsePanel = results.createResultListFragment(products);
            }
            results.setFragment(responsePanel);
        }
    }

    public ProductSearch () {
        add(new SearchProductForm("searchForm"));
        results = new ProductSearchPanel("panel");
        add(results);
    }

}
