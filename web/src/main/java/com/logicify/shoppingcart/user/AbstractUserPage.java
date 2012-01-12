package com.logicify.shoppingcart.user;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by IntelliJ IDEA.
 * User: knorr
 * Date: 1/12/12
 */
public class AbstractUserPage extends WebPage {

    protected void addControls() {
        add(new Label("title", "Admin page module"));
        add(getLink("linkToProductsListPage", "linksText", "View products", ProductsList.class));
        add(getLink("linkToCategoriesListPage", "linksText", "View categories", CategoriesList.class));
        add(getLink("linkToFindProductPage", "linksText", "Search", ProductSearch.class));
        add(getLink("Home", "linksText", "Home", com.logicify.shoppingcart.Index.class));
        add(new Label("Footer", "Logicify"));
    }

    protected BookmarkablePageLink getLink(String LinkWicketId, String LabelWicketId, String linksText, Class responsePage) {
        BookmarkablePageLink link = new BookmarkablePageLink(LinkWicketId, responsePage);
        link.add(new Label(LabelWicketId, linksText));
        return link;
    }

    public AbstractUserPage() {
        super();
        addControls();
    }

    public AbstractUserPage(PageParameters parameters) {
        super(parameters);
        addControls();
    }

}


