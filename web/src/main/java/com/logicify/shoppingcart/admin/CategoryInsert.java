package com.logicify.shoppingcart.admin;

import com.logicify.shoppingcart.Index;
import com.logicify.shoppingcart.domain.Category;
import com.logicify.shoppingcart.service.CategoryService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import java.util.*;

public class CategoryInsert extends AbstractAdminPage{

    @SpringBean(required = true)
    private CategoryService categoryService;

    private Category category;

    public CategoryInsert() {
        this.category = new Category();
        add(new FeedbackPanel("feedback"));
        Form form = new Form("form");
        add(form);
        form.setOutputMarkupId(true);
        form.add(new TextField("CategoryName", new PropertyModel<Category>(this.category, "name"))
                .setRequired(true)
                .add(StringValidator.LengthBetweenValidator.lengthBetween(3, 35))
                .add(new PatternValidator("[a-zA-Z0-9\\,\\+\\.\\s\\-\\_]{1,}"))
        );
        form.add(new TextArea("CategoryDescription", new PropertyModel<Category>(this.category, "description"))
                .setRequired(true)
                .add(StringValidator.LengthBetweenValidator.lengthBetween(3, 35))
                .add(new PatternValidator("[a-zA-Z0-9\\,\\+\\.\\s\\-\\_]{1,}"))
        );
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
