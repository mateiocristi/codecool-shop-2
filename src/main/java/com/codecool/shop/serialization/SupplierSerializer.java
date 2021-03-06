package com.codecool.shop.serialization;

import com.codecool.shop.model.Supplier;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SupplierSerializer implements JsonSerializer<Supplier>, JsonDeserializer<Supplier> {
    @Override
    public JsonElement serialize(Supplier supplier, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", supplier.getId());
        jsonObject.addProperty("name", supplier.getName());
        jsonObject.addProperty("description", supplier.getDescription());
        return jsonObject;
    }

    @Override
    public Supplier deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
//        ProductDao productDataStore = ProductDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
//        ProductService productService = new ProductService(productDataStore,productCategoryDataStore, supplierDataStore);

        Supplier supplier =  new Supplier(
                jsonObject.get("name").getAsString(),
                jsonObject.get("description").getAsString()
        );
        supplier.setId(Integer.parseInt(jsonObject.get("id").getAsString()));
        return supplier;
    }
}
