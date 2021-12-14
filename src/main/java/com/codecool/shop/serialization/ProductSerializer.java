package com.codecool.shop.serialization;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.service.ProductService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

public class ProductSerializer implements JsonSerializer<Product>, JsonDeserializer<Product> {

    @Override
    public JsonElement serialize(Product product, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", product.getId());
        jsonObject.addProperty("name", product.getName());
        jsonObject.addProperty("description", product.getDescription());
        jsonObject.addProperty("defaultPrice", product.getDefaultPrice());
        jsonObject.addProperty("defaultCurrency", product.getDefaultCurrency().toString());
        jsonObject.addProperty("productCategory", product.getProductCategory().getId());
        jsonObject.addProperty("supplier", product.getSupplier().getId());
        return jsonObject;
    }

    @Override
    public Product deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        ProductCategoryDao productCategoryDataStore = null;
        SupplierDao supplierDataStore = null;
        try {
            productCategoryDataStore = ProductCategoryDaoMem.getInstance();
            supplierDataStore = SupplierDaoMem.getInstance();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int categoryId = Integer.parseInt(jsonObject.get("productCategory").getAsString());
        ProductCategory category = productCategoryDataStore.find(categoryId);
        category.setProducts(new ArrayList<>());

        int supplierId = jsonObject.get("supplier").isJsonNull() ? 0 : Integer.parseInt(jsonObject.get("supplier").toString());
        Supplier supplier = supplierId != 0 ? supplierDataStore.find(supplierId) : new Supplier("Codecool", "Tech gadgets");
        supplier.setProducts(new ArrayList<>());

        Product product = new Product(
                jsonObject.get("name").getAsString(),
                new BigDecimal(jsonObject.get("defaultPrice").getAsString()),
                jsonObject.get("defaultCurrency").getAsString(),
                jsonObject.get("description").getAsString(),
                category,
                supplier
        );
        product.setId(Integer.parseInt(jsonObject.get("id").toString()));
        return product;
    }
}
