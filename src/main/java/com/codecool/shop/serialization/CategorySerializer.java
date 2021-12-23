package com.codecool.shop.serialization;

import com.codecool.shop.model.Category;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CategorySerializer implements JsonSerializer<Category>, JsonDeserializer<Category> {
    @Override
    public JsonElement serialize(Category productCategory, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", productCategory.getId());
        jsonObject.addProperty("name", productCategory.getName());
        jsonObject.addProperty("description", productCategory.getDescription());
        jsonObject.addProperty("department", productCategory.getDepartment());
        return jsonObject;
    }
    @Override
    public Category deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();


        Category productCategory = new Category(
                jsonObject.get("name").getAsString(),
                jsonObject.get("department").getAsString(),
                jsonObject.get("description").getAsString()
        );
        productCategory.setId(Integer.parseInt(jsonObject.get("id").getAsString()));
        return productCategory;
    }
}
