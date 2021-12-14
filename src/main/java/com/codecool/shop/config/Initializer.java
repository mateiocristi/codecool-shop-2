package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.math.BigDecimal;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = null;
        try {
            productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SupplierDao supplierDataStore = null;
        try {
            supplierDataStore = SupplierDaoMem.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //setting up a new supplier
//        Supplier amazon = new Supplier("Amazon", "Digital content and services");
//        Supplier lenovo = new Supplier("Lenovo", "Computers");
//        Supplier asus = new Supplier("Asus", "Laptops");
//        try {
//            supplierDataStore.add(amazon);
//            supplierDataStore.add(lenovo);
//            supplierDataStore.add(asus);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //setting up a new product category
//        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "Laptop. A PC packed into a smaller, more practical shape");
//        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
//        try {
//            productCategoryDataStore.add(tablet);
//            productCategoryDataStore.add(laptop);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            productDataStore.add(new Product("Asus TUF FX505DU Gaming Laptop", new BigDecimal("399.9"), "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", laptop, asus));
//            productDataStore.add(new Product("Lenovo Pad", new BigDecimal("49.9"), "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, lenovo));
//            productDataStore.add(new Product("Lenovo IdeaPad Mix 700", new BigDecimal("479"), "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
//            productDataStore.add(new Product("Amazon Fire HD 8", new BigDecimal("89"), "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
//            productDataStore.add(new Product("Asus ZenPad", new BigDecimal("49.9"), "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, asus));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//


    }
}
