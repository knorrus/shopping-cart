package com.logicify.shoppingcart.admin;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by IntelliJ IDEA.
 * User: knorr
 * Date: 1/12/12
 * Time: 1:17 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAdminPage extends WebPage{

    protected void addControls (){
        add(new Label("title", "Admin page module"));
        add(getLink("linkToProductsListPage", "linksText", "List of Existing products", ProductsList.class));
        add(getLink("linkToCategoriesListPage", "linksText", "List of Existing categories", CategoriesList.class ));
        add(getLink("linkToInsertProductPage", "linksText", "Insert new Product", ProductInsert.class));
        add(getLink("linkToInsertCategoryPage", "linksText", "Insert new Category", CategoryInsert.class));
        add(getLink("linkToFindProductPage", "linksText", "Find product", ProductSearch.class));
        add(getLink("Home", "linksText", "Home", com.logicify.shoppingcart.Index.class));
        add(new Label("Footer", "Logicify"));
    }

    protected BookmarkablePageLink getLink (String LinkWicketId, String LabelWicketId, String linksText, Class responsePage) {
        BookmarkablePageLink link = new BookmarkablePageLink(LinkWicketId, responsePage);
        link.add(new Label(LabelWicketId, linksText));
        return link;
    }


    public  AbstractAdminPage () {
        super();
        addControls();
    }

    public AbstractAdminPage (PageParameters parameters){
        super(parameters);
        addControls();
    }

}
