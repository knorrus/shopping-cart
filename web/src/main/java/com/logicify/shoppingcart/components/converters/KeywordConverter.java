package com.logicify.shoppingcart.components.converters;

import com.logicify.shoppingcart.domain.Keyword;
import org.apache.wicket.util.convert.IConverter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class KeywordConverter implements IConverter {

    public KeywordConverter() {
    }

    @Override
    public Object convertToObject(String s, Locale locale) {
        s = s.replaceAll("\\s{2,}", " ");
        s = s.replaceAll("\\s+\\,", ",");
        String[] keywords = s.split(", ");
        Set<Keyword> keySet = new HashSet<Keyword>();
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
            Set<Keyword> keySet = (Set<Keyword>) o;
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

	public void setLocale(Locale locale)
	{
	}

	public Locale getLocale()
	{
		return Locale.getDefault();
	}
}
