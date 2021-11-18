package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;

import java.io.IOException;
import java.util.List;

public interface ProductCategoryDao {

    void add(ProductCategory category) throws IOException;
    ProductCategory find(int id);
    ProductCategory find(String name);
    void remove(int id) throws IOException;

    List<ProductCategory> getAll();

}
