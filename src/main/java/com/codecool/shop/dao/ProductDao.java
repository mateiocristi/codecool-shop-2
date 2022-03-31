package com.codecool.shop.dao;

import com.codecool.shop.dao.DBmodels.ProductModel;

import java.io.IOException;
import java.util.List;

public interface ProductDao {

    void add(ProductModel product) throws IOException;
    ProductModel getById(int id);
    void remove(int id) throws IOException;

    List<ProductModel> getAll();
    List<ProductModel> getBySupplier(int supplierId);
    List<ProductModel> getByCategory(int productCategoryId);

}
