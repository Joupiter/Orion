package fr.orion.api.utils.json.serializer;

import com.google.gson.*;
import fr.orion.api.utils.json.GsonUtils;
import lombok.AccessLevel;
import lombok.Getter;

import java.lang.reflect.Type;

@Getter(value = AccessLevel.PRIVATE)
public class ApiImplSerializer<T extends ApiSerializable> implements JsonDeserializer<T> {

    private final Gson gson;

    public ApiImplSerializer() {
        this.gson = new Gson();
    }

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        return (T) getGson().fromJson(jsonElement, GsonUtils.getRegistries().get(type));
    }

}