package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.serialization.ProductSerializer;
import com.codecool.shop.serialization.SupplierSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SupplierDaoMem implements SupplierDao {

    private List<Supplier> data = new ArrayList<>();
    private static SupplierDaoMem instance = null;
    private Gson gson;
    private FileWriter fileWriter;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoMem() {
        try {
            data = new LinkedList<>(Arrays.asList(new Gson().fromJson(Files.readString(Path.of("./src/main/java/resources/suppliersDB.txt")), Supplier[].class)));
//            System.out.println("begin supplier");
//            data.forEach(System.out::println);
//            System.out.println("end");
        } catch (Exception ignored) {}
    }

    public static SupplierDaoMem getInstance() throws IOException {
        if (instance == null) {
            instance = new SupplierDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) throws IOException {
        if (data.stream().filter(t -> t.getName().equals(supplier.getName())).findFirst().orElse(null) == null) {
            supplier.setId(data.size() + 1);
            data.add(supplier);

            updateJson();
        }
    }

    @Override
    public Supplier find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) throws IOException {
        data.remove(find(id));

        updateJson();
    }

    @Override
    public List<Supplier> getAll() {
        return data;
    }

    private void updateJson() throws IOException {
        gson = new GsonBuilder().registerTypeAdapter(Supplier.class, new SupplierSerializer()).create();
        fileWriter = new FileWriter("./src/main/java/resources/suppliersDB.txt");
        fileWriter.write(gson.toJson(data));
        fileWriter.close();
    }
}
