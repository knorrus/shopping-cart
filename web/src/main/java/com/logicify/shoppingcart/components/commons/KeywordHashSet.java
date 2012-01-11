package com.logicify.shoppingcart.components.commons;

import com.logicify.shoppingcart.domain.Keyword;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: knorr
 * Date: 1/11/12
 * Time: 2:02 PM
 */
public class KeywordHashSet extends HashSet<Keyword> {

    public KeywordHashSet (java.util.Collection<Keyword> es) {
        super(es);
    }

}
