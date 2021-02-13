package me.kingtux.jdm.common;

import com.google.gson.*;
import me.kingtux.mavenlibrary.Repository;

import java.lang.reflect.Type;

public class RepositorySerializer implements JsonSerializer<Repository>, JsonDeserializer<Repository> {
    @Override
    public Repository deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new Repository(jsonObject.get("url").getAsString(), jsonObject.get("id").getAsString());
    }

    @Override
    public JsonElement serialize(Repository src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("url", src.getUrl());
        object.addProperty("id", src.getRepoID());
        return object;
    }
}
