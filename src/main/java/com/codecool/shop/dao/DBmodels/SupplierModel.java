package com.codecool.shop.dao.DBmodels;

import com.codecool.shop.model.Supplier;

public class SupplierModel extends DBBaseModel{

    public SupplierModel(Supplier supplier) {
        super(supplier.getName(), supplier.getDescription());
    }

    public SupplierModel(String name) {
        super(name);
    }

    public SupplierModel(String name, String description) {
        super(name, description);
    }
}
