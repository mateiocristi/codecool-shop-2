package com.codecool.shop.service;

import com.codecool.shop.dao.CategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Category;
import com.codecool.shop.model.Supplier;

import java.util.List;

//public class ProductService{
//    private ProductDao productDao;
//    private CategoryDao productCategoryDao;
//    private SupplierDao supplierDao;
//
//    public ProductService(ProductDao productDao, CategoryDao productCategoryDao, SupplierDao supplierDao) {
//        this.productDao = productDao;
//        this.supplierDao = supplierDao;
//        this.productCategoryDao = productCategoryDao;
//    }
//
//    public Category getProductCategory(int categoryId){
//        return productCategoryDao.getByName(categoryId);
//    }
//
//    public List<Product> getProductsForId(int categoryId){
//        var category = productCategoryDao.getByName(categoryId);
//        return productDao.getBy(category);
//    }
//
//    public List<Product> getProductsForCategory(String categoryName){
//        var category = productCategoryDao.getByName(categoryName);
//        return productDao.getBy(category);
//    }
//
//
//    public Supplier getProductSupplier(int supplierId) {
//        return supplierDao.getById(supplierId);
//    }
//
//
//}
