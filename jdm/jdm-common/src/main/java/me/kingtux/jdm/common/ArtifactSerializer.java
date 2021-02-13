package me.kingtux.jdm.common;

import com.google.gson.*;

import java.lang.reflect.Type;


public class ArtifactSerializer implements JsonSerializer<JDMArtifact>, JsonDeserializer<JDMArtifact> {
    @Override
    public JDMArtifact deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new JDMArtifact(jsonObject.get("groupID").getAsString(), jsonObject.get("artifactID").getAsString(), jsonObject.get("version").getAsString());
    }

    @Override
    public JsonElement serialize(JDMArtifact src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("groupID", src.getGroupID());
        object.addProperty("artifactID", src.getArtifactID());
        object.addProperty("version", src.getVersion());
        return object;
    }
}
