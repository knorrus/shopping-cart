package com.logicify.shoppingcart;

import com.logicify.shoppingcart.domain.Product;
import com.logicify.shoppingcart.service.ProductService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: knorr
 * Date: 12/17/11
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class InsertProduct extends WebPage{

    private Product product;
    @SpringBean(required = true)
    private ProductService service;


    public InsertProduct() {
        product = new Product();
        Form form = new Form("form");
        add(form);
        form.setOutputMarkupId(true);
        form.add(new TextField("ProductName", new PropertyModel(product, "name")));
        form.add(new TextField("ProductPrice", new PropertyModel(product, "price")));
        form.add(new TextArea("ProductDescription", new PropertyModel(product, "description")));
        form.add(new Button("submitForm"){
            @Override
            public void onSubmit(){
                product.setDate(new Date());
                service.insertProduct(product);
                setResponsePage(Index.class);
            }
        });
    }

}
