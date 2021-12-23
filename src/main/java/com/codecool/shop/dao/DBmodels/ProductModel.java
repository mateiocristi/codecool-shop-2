package com.codecool.shop.dao.DBmodels;

import com.codecool.shop.model.BaseModel;
import com.codecool.shop.model.Product;

import java.math.BigDecimal;
import java.util.Currency;

public class ProductModel extends BaseModel {

    private String defaultPrice;
    private String actualPrice;
    private String defaultCurrency;
    private int categoryId;
    private int supplierId;

    public ProductModel(Product product, int categoryId, int supplierId) {
        super(product.getName(), product.getDescription());
        this.id = product.getId();
        this.defaultCurrency = product.getDefaultCurrency().toString();
        this.defaultPrice = product.getDefaultPrice().toString();
        this.actualPrice = product.getActualPrice().toString();
        this.categoryId = categoryId;
        this.supplierId = supplierId;
    }

    public ProductModel(String name, String description, String defaultPrice, String actualPrice, String defaultCurrency, int categoryId, int supplierId) {
        super(name, description);
        this.defaultPrice = defaultPrice;
        this.actualPrice = actualPrice;
        this.defaultCurrency = defaultCurrency;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
    }

    public String getDefaultPrice() {
        return defaultPrice;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getSupplierId() {
        return supplierId;
    }

}
