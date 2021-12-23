package com.codecool.shop.dao.DBmodels;

import com.codecool.shop.model.Category;

public class CategoryModel extends DBBaseModel {

    private String department;

    public CategoryModel(Category category) {
        super(category.getName(), category.getDescription());
        this.department = category.getDepartment();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
