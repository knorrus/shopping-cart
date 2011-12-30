package com.logicify.shoppingcart.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: pavel
 * Date: 11/23/11
 * Time: 10:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Category implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Date date;
    private Set<Product> products = new HashSet<Product>();

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set getProducts() {
        return this.products;
    }

    public void setProducts(Set products) {
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category temp = (Category) o;
        if (this.id.equals(temp.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.intValue();
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
}
