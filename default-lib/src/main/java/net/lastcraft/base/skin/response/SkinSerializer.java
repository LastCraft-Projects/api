package net.lastcraft.base.skin.response;

import com.google.gson.*;

import java.lang.reflect.Type;

public final class SkinSerializer implements JsonDeserializer<SkinProperty> {

    @Override
    public SkinProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!(json instanceof JsonArray)) {
            return null;
        }

        for (JsonElement jsonElement : ((JsonArray) json)) {
            if (jsonElement instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) jsonElement;

                if (jsonObject.has("signature")) {
                    String name = jsonObject.getAsJsonPrimitive("name").getAsString();
                    String value = jsonObject.getAsJsonPrimitive("value").getAsString();
                    String signature = jsonObject.getAsJsonPrimitive("signature").getAsString();
                    return new SkinProperty(name, value, signature);
                }
            }
        }

        return null;
    }
}
