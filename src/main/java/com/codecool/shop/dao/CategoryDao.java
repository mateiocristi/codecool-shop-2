package com.codecool.shop.dao;

import com.codecool.shop.model.Category;

import java.io.IOException;
import java.util.List;

public interface CategoryDao {

    void add(Category category) throws IOException;
    Category getById(int id);
    Category getByName(String name);
    void remove(int id) throws IOException;

    List<Category> getAll();

}
