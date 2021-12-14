package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.serialization.CategorySerializer;
import com.codecool.shop.serialization.ProductSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ProductCategoryDaoMem implements ProductCategoryDao {

    private List<ProductCategory> data = new ArrayList<>();
    private static ProductCategoryDaoMem instance = null;
    private Gson gson;
    private FileWriter fileWriter;
    private File file;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoMem() {
         try {
             data = new LinkedList<>(Arrays.asList(new Gson().fromJson(Files.readString(Path.of("./src/main/java/resources/categoriesDB.txt")), ProductCategory[].class)));
//             System.out.println("begin category");
//             data.forEach(System.out::println);
//             System.out.println("end");
         } catch (Exception ignored) {}
    }

    public static ProductCategoryDaoMem getInstance() throws IOException {
        if (instance == null) {
            instance = new ProductCategoryDaoMem();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) throws IOException {
        if (data.stream().filter(t -> Objects.equals(t.getName(), category.getName())).findFirst().orElse(null) == null) {
            category.setId(data.size() + 1);
            data.add(category);

            updateJson();
        }
    }

    @Override
    public ProductCategory find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public ProductCategory find(String name) {
        return data.stream().filter(t -> Objects.equals(t.getName(), name)).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) throws IOException {
        data.remove(find(id));

        updateJson();
    }

    @Override
    public List<ProductCategory> getAll() {
        return data;
    }

    private void updateJson() throws IOException {
        gson = new GsonBuilder().registerTypeAdapter(ProductCategory.class, new CategorySerializer()).create();
        fileWriter = new FileWriter("./src/main/java/resources/categoriesDB.txt");
        fileWriter.write(gson.toJson(data));
        fileWriter.close();
    }
}
