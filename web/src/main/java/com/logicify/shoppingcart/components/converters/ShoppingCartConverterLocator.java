package com.logicify.shoppingcart.components.converters;

import com.logicify.shoppingcart.components.commons.KeywordHashSet;
import com.logicify.shoppingcart.domain.Keyword;
import org.apache.wicket.ConverterLocator;
import java.util.HashSet;

public class ShoppingCartConverterLocator extends ConverterLocator {

    public ShoppingCartConverterLocator() {
        super();
        set(KeywordHashSet.class, KeywordConverter.INSTANCE);
    }

}

