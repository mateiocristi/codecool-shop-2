package com.codecool.shop.serialization;

import com.codecool.shop.dao.CategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.database.DatabaseManager;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Category;
import com.codecool.shop.model.Supplier;
import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductSerializer implements JsonSerializer<Product>, JsonDeserializer<Product> {

    @Override
    public JsonElement serialize(Product product, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", product.getId());
        jsonObject.addProperty("name", product.getName());
        jsonObject.addProperty("description", product.getDescription());
        jsonObject.addProperty("defaultPrice", product.getDefaultPrice());
        jsonObject.addProperty("defaultCurrency", product.getDefaultCurrency().toString());
        jsonObject.addProperty("productCategory", product.getCategory().getId());
        jsonObject.addProperty("supplier", product.getSupplier().getId());
        return jsonObject;
    }

    @Override
    public Product deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        CategoryDao productCategoryDataStore = null;
        SupplierDao supplierDataStore = null;
        DatabaseManager databaseManager = null;
        databaseManager = new DatabaseManager();
        databaseManager.run();


        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int categoryId = Integer.parseInt(jsonObject.get("productCategory").getAsString());
        Category category = databaseManager.getCategory(categoryId);
        category.setProducts(new ArrayList<>());

        int supplierId = jsonObject.get("supplier").isJsonNull() ? 0 : Integer.parseInt(jsonObject.get("supplier").toString());
        Supplier supplier = supplierId != 0 ? supplierDataStore.getById(supplierId) : new Supplier("Codecool", "Tech gadgets");
        supplier.setProducts(new ArrayList<>());

        Product product = new Product(
                jsonObject.get("name").getAsString(),
                jsonObject.get("defaultPrice").getAsString(),
                new BigDecimal(jsonObject.get("defaultCurrency").getAsString()),
                jsonObject.get("description").getAsString(),
                category,
                supplier
        );
        product.setId(Integer.parseInt(jsonObject.get("id").toString()));
        return product;
    }
}
