package com.codecool.shop.dao.DBmodels;

public abstract class DBBaseModel {
    protected int id;
    protected String name;
    protected String description;

    public DBBaseModel(String name) {
        this.name = name;
    }

    public DBBaseModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setDescription(String description) {
        this.description = description;
    }
}
