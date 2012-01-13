package com.logicify.shoppingcart.user;

import com.logicify.shoppingcart.domain.Product;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProductSearchPanel extends Panel {

    private Fragment currentFragment;

    public ProductSearchPanel(String id) {
        super(id);
        setOutputMarkupPlaceholderTag(true);
        currentFragment = InitEmptyFragment();
        add(currentFragment);
    }

    protected Fragment InitEmptyFragment() {
        Fragment fragment = new Fragment("searchResult", "empty", this);
        return fragment;
    }


    public void setFragment(Fragment fragment) {
        currentFragment.replaceWith(fragment);
        currentFragment = fragment;
    }

    public Fragment createResultListFragment(List<Product> list, String sortOrder, String sortCriteria) {
        Fragment fragment = new Fragment("searchResult", "results", this);
        PageableListView<Product> listView = new PageableListView<Product>("productList", new ArrayList<Product>(), 10) {
            @Override
            protected void populateItem(ListItem<Product> productListItem) {
                Product product = productListItem.getModelObject();
                BookmarkablePageLink<Product> linkToProduct = new BookmarkablePageLink<Product>("linkToProduct", ProductDetails.class);
                linkToProduct.getPageParameters().set("id", product.getId());
                linkToProduct.add(new Label("productName", product.getName()));
                productListItem.add(new Label("productPrice", product.getPrice().toString()));
                productListItem.add(linkToProduct);
            }
        };
        if (!list.isEmpty()) {

            if (sortCriteria.equals("name")){
                Collections.sort(list, Product.getNameComparator());
            }
            else {
                Collections.sort(list, Product.getPriceComparator());
            }
            if (sortOrder.equals("desc")){
                Collections.reverse(list);
            }
            listView.setList(list);
        }
        fragment.add(listView);
        fragment.add(new PagingNavigator("navigator", listView));
        return fragment;
    }

    public Fragment createNoResultListFragment(String errMessage) {
        Fragment fragment = new Fragment("searchResult", "noResults", this);
        fragment.add(new Label("errorMessage", errMessage));
        return fragment;
    }
}
