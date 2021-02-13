package me.kingtux.jdm.common;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.kingtux.mavenlibrary.Repository;

import java.lang.reflect.Type;
import java.util.List;


public class ReportSerializer implements JsonSerializer<Report>, JsonDeserializer<Report> {
    @Override
    public Report deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        List<Repository> repositoryList = context.deserialize(jsonObject.get("repositories"), new TypeToken<List<Repository>>() {
        }.getType());
        List<JDMArtifact> artifactList = context.deserialize(jsonObject.get("dependencies"), new TypeToken<List<JDMArtifact>>() {
        }.getType());
        return new Report(repositoryList, artifactList);
    }

    @Override
    public JsonElement serialize(Report src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("repositories", context.serialize(src.getRepositoryList()));
        object.add("dependencies", context.serialize(src.getArtifactList()));
        return object;
    }
}
