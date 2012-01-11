package com.logicify.shoppingcart.components.converters;

import com.logicify.shoppingcart.components.commons.KeywordHashSet;
import com.logicify.shoppingcart.domain.Keyword;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.AbstractConverter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

public class KeywordConverter extends AbstractConverter{

    public KeywordConverter() {
    }

    public static final IConverter INSTANCE = new KeywordConverter();

    @Override
    protected Class getTargetType() {
        return HashSet.class;
    }


    @Override
    public Object convertToObject(String s, Locale locale) {
        s = s.replaceAll("\\s{2,}", " ");
        s = s.replaceAll("\\s+\\,", ",");
        s = s.replaceAll("\\,+\\s", ",");
        String[] keywords = s.split(",");
        KeywordHashSet keySet = new KeywordHashSet(new HashSet<Keyword>());
        for (String word : keywords) {
            Keyword key = new Keyword();
            key.setKey(word);
            key.setEntityName("Product");
            keySet.add(key);
        }
        return keySet;
    }

    @Override
    public String convertToString(Object o, Locale locale) {
            KeywordHashSet keySet = (KeywordHashSet) o;
            String tags = null;
            if(keySet == null){
                return tags;
            }
            Iterator<Keyword> iterator = keySet.iterator();
            while (iterator.hasNext()){
                tags += iterator.next().getKey() + ", ";
            }
            return tags;
    }
}
