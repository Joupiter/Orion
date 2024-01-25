package fr.orion.api.utils.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PRIVATE)
public class JsonObjectBuilder {

    private final JsonObject jsonObject;

    public JsonObjectBuilder() {
        this.jsonObject = new JsonObject();
    }

    public JsonObjectBuilder(JsonElement jsonElement) {
        this.jsonObject = jsonElement.getAsJsonObject();
    }

    public JsonObjectBuilder add(String key, String value) {
        getJsonObject().addProperty(key, value);
        return this;
    }

    public JsonObjectBuilder add(String key, boolean value) {
        getJsonObject().addProperty(key, value);
        return this;
    }

    public JsonObjectBuilder add(String key, char value) {
        getJsonObject().addProperty(key, value);
        return this;
    }

    public JsonObjectBuilder add(String key, Number value) {
        getJsonObject().addProperty(key, value);
        return this;
    }

    public JsonObjectBuilder add(String key, JsonElement value) {
        getJsonObject().add(key, value);
        return this;
    }

    public JsonObjectBuilder remove(String key) {
        getJsonObject().remove(key);
        return this;
    }

    public JsonObject build() {
        return getJsonObject();
    }

}