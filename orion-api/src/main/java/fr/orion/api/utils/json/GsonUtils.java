package fr.orion.api.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.orion.api.economy.currency.DoubleCurrency;
import fr.orion.api.economy.currency.IntegerCurrency;
import fr.orion.api.utils.json.adapter.CurrencyAdapter;
import fr.orion.api.utils.json.serializer.ApiImplSerializer;
import fr.orion.api.utils.json.serializer.ApiSerializable;
import fr.orion.api.utils.json.serializer.GsonImplementation;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@UtilityClass
public class GsonUtils {

    private final Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(ApiSerializable.class, new ApiImplSerializer<>())
            .registerTypeHierarchyAdapter(ApiSerializable.class, new ApiImplSerializer<>())
            .registerTypeAdapter(IntegerCurrency.class, new CurrencyAdapter.IntegerCurrencyAdapter())
            .registerTypeAdapter(DoubleCurrency.class, new CurrencyAdapter.DoubleCurrencyAdapter())
            .create();

    private final Map<Type, Class<?>> registries = new HashMap<>();

    public Gson getGson() {
        return gson;
    }

    public Map<Type, Class<?>> getRegistries() {
        return registries;
    }

    public void addRegistry(Class<?> clazz) {
        log.info("Class registered: {}", clazz.getSimpleName());
        getRegistries().put(clazz.getAnnotation(GsonImplementation.class).of(), clazz);
    }

}