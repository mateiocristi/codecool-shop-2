package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.serialization.ProductSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javassist.bytecode.analysis.Type;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoMem implements ProductDao {

    private List<Product> data = new ArrayList<>();
    private static ProductDaoMem instance = null;
    private Gson gson;
    private FileWriter fileWriter ;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoMem() {
        System.out.println("yyy");
        try {
            String productJson = Files.readString(Path.of("./src/main/java/resources/productsDB.txt"));
            java.lang.reflect.Type productListType = new TypeToken<List<Product>>() {}.getType();
            gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductSerializer()).create();
            data = gson.fromJson(productJson, productListType);

//            System.out.println("begin ");
//            products.forEach(System.out::println);
//            System.out.println("end");
//            data = new LinkedList<>(Arrays.asList(new Gson().fromJson(Files.readString(Path.of("./src/main/java/resources/productsDB.txt")), Product[].class)));

        } catch (Exception e) {
            System.out.println("exception: " + e);
        }
    }

    public static ProductDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) throws IOException {
        product.setId(data.size() + 1);
        data.add(product);

        updateJson();
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) throws IOException {
        data.remove(find(id));

        updateJson();
    }

    @Override
    public List<Product> getAll() {
        return data;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return data.stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return data.stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }

    @Override
    public Product getBy(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    private void updateJson() throws IOException {
        gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductSerializer()).create();
        fileWriter = new FileWriter("./src/main/java/resources/productsDB.txt");
        fileWriter.write(gson.toJson(data));
        fileWriter.close();
    }
}
