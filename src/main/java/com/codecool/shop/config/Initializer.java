package com.codecool.shop.config;

import com.codecool.shop.dao.CategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.database.DatabaseManager;
import com.codecool.shop.model.Category;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.math.BigDecimal;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseManager databaseManager = null;
        databaseManager = new DatabaseManager();
        databaseManager.run();

//        setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        Supplier asus = new Supplier("Asus", "Laptops");
//        try {
//            databaseManager.addSupplier(amazon);
//            databaseManager.addSupplier(lenovo);
//            databaseManager.addSupplier(asus);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //setting up a new product category
        Category laptop = new Category("Laptop", "Hardware", "Laptop. A PC packed into a smaller, more practical shape");
        Category tablet = new Category("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
//        try {
//            databaseManager.addCategory(tablet);
//            databaseManager.addCategory(laptop);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            databaseManager.addProduct(new Product("Asus TUF FX505DU Gaming Laptop", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", new BigDecimal("399.9"), "USD", laptop, asus));
//            databaseManager.addProduct(new Product("Lenovo Pad", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", new BigDecimal("49.9"), "USD", tablet, lenovo));
//            databaseManager.addProduct(new Product("Lenovo IdeaPad Mix 700", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.",new BigDecimal("479"), "USD", tablet, lenovo));
//            databaseManager.addProduct(new Product("Amazon Fire HD 8", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.",new BigDecimal("89"), "USD", tablet, amazon));
//            databaseManager.addProduct(new Product("Asus ZenPad", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.",new BigDecimal("49.9"), "USD", tablet, asus));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //to do: run from memory or from db




    }
}
