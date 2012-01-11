package com.logicify.shoppingcart.dao;

import com.logicify.shoppingcart.domain.Keyword;

import java.sql.SQLException;
import java.util.Collection;

public interface KeywordsDao {

     public void insertKeyword(Keyword keyword) throws SQLException;

     public void insertKeywords(Collection<Keyword> collection) throws SQLException;

}
