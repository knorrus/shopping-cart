package com.logicify.shoppingcart.admin;

import com.logicify.shoppingcart.Index;
import com.logicify.shoppingcart.domain.Category;
import com.logicify.shoppingcart.service.CategoryService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: knorr
 * Date: 12/30/11
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryInsert extends WebPage{

    @SpringBean(required = true)
    private CategoryService categoryService;

    private Category category;

    public CategoryInsert() {
        this.category = new Category();
        Form form = new Form("form");
        add(form);
        form.setOutputMarkupId(true);
        form.add(new TextField("CategoryName", new PropertyModel<Category>(this.category, "name")).setRequired(true));
        form.add(new TextArea("CategoryDescription", new PropertyModel<Category>(this.category, "description")).setRequired(true));
        form.add(new Button("submitForm") {
            @Override
            public void onSubmit() {
                category.setDate(new Date());
                categoryService.insertCategory(category);
                setResponsePage(Index.class);
            }
        });
    }

}
