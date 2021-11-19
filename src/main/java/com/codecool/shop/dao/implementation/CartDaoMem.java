package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.serialization.ProductSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
;import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CartDaoMem implements CartDao {

    private List<Product> data = new ArrayList<>();
    private static CartDaoMem instance = null;
    Gson gson;
    FileWriter fileWriter;

    private CartDaoMem() {
        try {
            String productJson = Files.readString(Path.of("./src/main/java/resources/cartProductsDB.txt"));
            java.lang.reflect.Type productListType = new TypeToken<List<Product>>() {}.getType();
            gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductSerializer()).create();
            data = gson.fromJson(productJson, productListType) == null ? new ArrayList<>() : gson.fromJson(productJson, productListType);

        } catch (Exception ignored) {
        }
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void add(Product product) throws IOException {
        data.add(product);

        updateJson();
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

    private void updateJson() throws IOException {
        gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductSerializer()).create();
        fileWriter = new FileWriter("./src/main/java/resources/cartProductsDB.txt");
        fileWriter.write(gson.toJson(data));
        fileWriter.close();
    }
}
