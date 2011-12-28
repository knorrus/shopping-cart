package com.logicify.shoppingcart.domain;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 11/21/11
 * Time: 6:43 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Product implements Serializable{

    private Long id;
    private String name;
    private String description;
    private Date date;
    private BigDecimal price;
    private Set categories = new HashSet();

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set getCategories() {
        return this.categories;
    }

    public void setCategories(Set categories) {
        this.categories = categories;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public int hashCode(){
        return this.id.intValue();
    }

    @Override
    public boolean equals (Object obj){
        if (obj == this){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (this.getClass() == obj.getClass()){
            Product temp = (Product)obj;
            if (this.id.equals(temp.getId())) {
                return  true;
            }
        }
        return false;
    }

}
