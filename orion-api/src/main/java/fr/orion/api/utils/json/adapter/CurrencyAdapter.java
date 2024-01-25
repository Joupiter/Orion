package fr.orion.api.utils.json.adapter;

import com.google.gson.*;
import fr.orion.api.economy.CurrencyFactory;
import fr.orion.api.economy.currency.DoubleCurrency;
import fr.orion.api.economy.currency.IntegerCurrency;

import java.lang.reflect.Type;

public class CurrencyAdapter {

    public static class IntegerCurrencyAdapter implements JsonSerializer<IntegerCurrency>, JsonDeserializer<IntegerCurrency> {

        @Override
        public JsonElement serialize(IntegerCurrency currency, Type type, JsonSerializationContext context) {
            return CurrencyFactory.currencyToJsonElement(currency);
        }

        @Override
        public IntegerCurrency deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            return CurrencyFactory.newIntegerCurrency(jsonElement.getAsJsonObject());
        }

    }

    public static class DoubleCurrencyAdapter implements JsonSerializer<DoubleCurrency>, JsonDeserializer<DoubleCurrency> {

        @Override
        public JsonElement serialize(DoubleCurrency currency, Type type, JsonSerializationContext context) {
            return CurrencyFactory.currencyToJsonElement(currency);
        }

        @Override
        public DoubleCurrency deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            return CurrencyFactory.newDoubleCurrency(jsonElement.getAsJsonObject());
        }

    }

}