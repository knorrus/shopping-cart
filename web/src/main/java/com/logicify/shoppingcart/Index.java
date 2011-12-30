package com.logicify.shoppingcart;

import com.logicify.shoppingcart.admin.InsertProduct;
import com.logicify.shoppingcart.admin.ListOfExistingCategories;
import com.logicify.shoppingcart.admin.ListOfExistingProducts;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 12/6/11
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Index extends WebPage {
    public Index() {
        Link adminModule = new Link("AdminModule") {
            @Override
            public void onClick() {
                setResponsePage(new com.logicify.shoppingcart.admin.Index());
            }
        };
        adminModule.add(new Label("linksText", "Administrative interface"));
        add(adminModule);
    }
}
