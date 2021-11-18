package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;

import java.io.IOException;
import java.util.List;

public interface SupplierDao {

    void add(Supplier supplier) throws IOException;
    Supplier find(int id);
    void remove(int id) throws IOException;

    List<Supplier> getAll();
}
