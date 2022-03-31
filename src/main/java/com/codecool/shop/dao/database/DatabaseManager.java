package com.codecool.shop.dao.database;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.DBmodels.ProductModel;
import com.codecool.shop.dao.DBmodels.UserModel;
import com.codecool.shop.model.Category;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseManager {

    ProductDao productDao;
    CategoryDao categoryDao;
    SupplierDao supplierDao;
    UserDao userDao;

    public User addUser(User user) {
        return userDao.add(user);
    }

    public UserModel checkUser(String email, String password) {
        User user = userDao.checkUser(email, password);
        UserModel userModel = new UserModel(user);
        if (!Objects.equals(user.getCart(), ""))
            userModel.setCartProductsId(stringArrayToListOfInts(user.getCart().split(",")));
        if (!Objects.equals(user.getOrders(), ""))
            userModel.setOrdersId(stringArrayToListOfInts(user.getOrders().split(",")));
        return userModel;
    }

    public void updateCart(UserModel userModel) {
        User user = new User(userModel);
        userDao.updateCart(user);
    }

    public void updateOrder(UserModel userModel) {
        User user = new User(userModel);
        userDao.updateOrder(user);
    }

    public UserModel getUser(int userId) {
        User user = userDao.get(userId);
        UserModel userModel = new UserModel(user);
        if (!Objects.equals(user.getCart(), ""))
            userModel.setCartProductsId(stringArrayToListOfInts(user.getCart().split(",")));
        if (!Objects.equals(user.getOrders(), ""))
            userModel.setOrdersId(stringArrayToListOfInts(user.getOrders().split(",")));
        return userModel;
    }

    public void addCategory(Category category) throws IOException {
        categoryDao.add(category);
    }

    public Category getCategory(int id) {
        return categoryDao.getById(id);
    }
    public Category getCategory(String name) {
        return categoryDao.getByName(name);
    }

    public List<Category> getAllCategories() {
        return  categoryDao.getAll();
    }

    public void addSupplier(Supplier supplier) throws IOException {
        supplierDao.add(supplier);
    }

    public Supplier getSupplier(int id) {
        return supplierDao.getById(id);
    }

    public Supplier getByName(String name) {
        return supplierDao.getByName(name);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierDao.getAll();
    }

    public void addProduct(Product product) throws IOException {
        Category category = categoryDao.getById(product.getCategory().getId());
        Supplier supplier = supplierDao.getById(product.getSupplier().getId());
        // add category and supplier if not already in database

            categoryDao.add(product.getCategory());

            supplierDao.add(product.getSupplier());

        ProductModel productModel = new ProductModel(product, product.getCategory().getId(), product.getSupplier().getId());
        productDao.add(productModel);
    }

    public Product getProduct(int id) {
        ProductModel productModel = productDao.getById(id);
        Supplier supplier = supplierDao.getById(productModel.getSupplierId());
        Category category = categoryDao.getById(productModel.getCategoryId());
        return new Product(productModel, category, supplier);
    }

    public List<Product> getAllProducts() {
        List<ProductModel> productModelList = productDao.getAll();
        return populateProducts(productModelList);
    }

    public List<Product> getProductsByCategory(int categoryId) {
        List<ProductModel> productModelList = productDao.getByCategory(categoryId);
        return populateProducts(productModelList);
    }

    public List<Product> getProductsBySupplier(int supplierId) {
        List<ProductModel> productModelList = productDao.getByCategory(supplierId);
        return populateProducts(productModelList);
    }



    public void run() {
        try {
            setup();
        } catch (SQLException throwables) {
            System.err.println("Could not connect to the database.");
        }
    }

    private void setup() throws SQLException {
        DataSource dataSource = connect();
        productDao = new ProductDaoJdbc(dataSource);
        categoryDao = new CategoryDaoJdbc(dataSource);
        supplierDao = new SupplierDaoJdbc(dataSource);
        userDao = new UserDaoJdbc(dataSource);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        ApplicationProperties properties = new ApplicationProperties();
        dataSource.setDatabaseName(properties.readProperty("database"));
        dataSource.setUser(properties.readProperty("user"));
        dataSource.setPassword(properties.readProperty("password"));

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");

        return dataSource;
    }

    private List<Product> populateProducts(List<ProductModel> productModelList) {
        List<Product> products = new ArrayList<>();
        for (ProductModel productModel : productModelList) {

            Category category = categoryDao.getById(productModel.getCategoryId());
            Supplier supplier = supplierDao.getById(productModel.getSupplierId());

            Product product = new Product(productModel.getName(), productModel.getDescription(), new BigDecimal(productModel.getDefaultPrice()), new BigDecimal( productModel.getActualPrice()), productModel.getDefaultCurrency(), category, supplier);
            product.setId(productModel.getId());
            products.add(product);
        }
        return products;
    }

    private List<Integer> stringArrayToListOfInts(String[] text) {
        return Arrays.stream(text).map(Integer::valueOf).collect(Collectors.toList());
    }
}
