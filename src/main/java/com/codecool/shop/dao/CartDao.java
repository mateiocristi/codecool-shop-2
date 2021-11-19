package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.io.IOException;
import java.util.List;

public interface CartDao {
    void add(Product product) throws IOException;
    Product find(int id);
    void remove(int id) throws IOException;
    List<Product> getAll();
}
