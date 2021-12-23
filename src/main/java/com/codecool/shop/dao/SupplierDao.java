package com.codecool.shop.dao;

import com.codecool.shop.dao.DBmodels.SupplierModel;
import com.codecool.shop.model.Supplier;

import java.io.IOException;
import java.util.List;

public interface SupplierDao {

    void add(Supplier supplier) throws IOException;
    Supplier getById(int id);
    Supplier getByName(String name);
    void remove(int id) throws IOException;

    List<Supplier> getAll();
}
