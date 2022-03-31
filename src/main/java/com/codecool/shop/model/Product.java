package com.codecool.shop.model;

import com.codecool.shop.dao.DBmodels.ProductModel;

import java.math.BigDecimal;
import java.util.Currency;

public class Product extends BaseModel {

    private BigDecimal defaultPrice;
    private BigDecimal actualPrice;
    private Currency defaultCurrency;
    private Category productCategory;
    private Supplier supplier;

    public Product(String name, String description, BigDecimal defaultPrice, BigDecimal actualPrice , String currencyString, Category productCategory, Supplier supplier) {
        super(name, description);
        this.setPrice(defaultPrice, currencyString);
        this.setActualPrice(actualPrice);
        this.setSupplier(supplier);
        this.setCategory(productCategory);

    }

    public Product(String name, String description, BigDecimal defaultPrice, String currencyString, Category productCategory, Supplier supplier) {
        super(name, description);
        this.setPrice(defaultPrice, currencyString);
        this.setActualPrice(defaultPrice);
        this.setSupplier(supplier);
        this.setCategory(productCategory);

    }

    public Product(ProductModel productModel, Category category, Supplier supplier) {
        super(productModel.getName(), productModel.getDescription());
        this.id = productModel.getId();
        this.setPrice(new BigDecimal(productModel.getDefaultPrice()), productModel.getDefaultCurrency());
        this.setActualPrice(new BigDecimal(productModel.getActualPrice()));
        this.setSupplier(supplier);
        this.setCategory(category);
    }

    public BigDecimal getDefaultPrice() {
        return defaultPrice;
    }


    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }


    public String getPrice() {
        return String.valueOf(this.defaultPrice) + " " + this.defaultCurrency.toString();
    }

    public void setPrice(BigDecimal price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = Currency.getInstance(currency);
    }

    public Category getCategory() {
        return productCategory;
    }

    public void setCategory(Category productCategory) {
        this.productCategory = productCategory;
        this.productCategory.addProduct(this);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        this.supplier.addProduct(this);
    }

//    @Override
//    public String toString() {
//        return String.format("id: %1$d, " +
//                        "name: %2$s, " +
//                        "defaultPrice: %3$f, " +
//                        "defaultCurrency: %4$s, " +
//                        "productCategory: %5$s, " +
//                        "supplier: %6$s",
//                this.id,
//                this.name,
//                this.defaultPrice,
//                this.defaultCurrency.toString(),
//                this.productCategory.getName(),
//                this.supplier.getName());
//    }
}
