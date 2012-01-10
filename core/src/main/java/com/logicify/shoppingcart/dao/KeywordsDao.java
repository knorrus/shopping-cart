package com.logicify.shoppingcart.dao;

import com.logicify.shoppingcart.domain.Keyword;

import java.sql.SQLException;

public interface KeywordsDao {

     public void insertKeyword(Keyword keyword) throws SQLException;

}
