package fr.orion.api.economy;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.orion.api.economy.currency.DoubleCurrency;
import fr.orion.api.economy.currency.IntegerCurrency;
import fr.orion.api.utils.json.JsonObjectBuilder;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CurrencyFactory {

    public IntegerCurrency newIntegerCurrency(String name, String symbol) {
        return newIntegerCurrency(name, symbol, 0);
    }

    public IntegerCurrency newIntegerCurrency(String name, String symbol, int amount) {
        return new IntegerCurrency(name, symbol, amount);
    }

    public DoubleCurrency newDoubleCurrency(String name, String symbol) {
        return newDoubleCurrency(name, symbol, 0);
    }

    public DoubleCurrency newDoubleCurrency(String name, String symbol, double amount) {
        return new DoubleCurrency(name, symbol, amount);
    }

    public JsonElement currencyToJsonElement(Currency<?> currency) {
        return new JsonObjectBuilder()
                .add("name", currency.getName())
                .add("symbol", currency.getSymbol())
                .add("amount", currency.getAmount()).build();
    }

    public IntegerCurrency newIntegerCurrency(JsonObject jsonObject) {
        return newIntegerCurrency(
                jsonObject.get("name").getAsString(),
                jsonObject.get("symbol").getAsString(),
                jsonObject.get("amount").getAsInt());
    }

    public DoubleCurrency newDoubleCurrency(JsonObject jsonObject) {
        return newDoubleCurrency(
                jsonObject.get("name").getAsString(),
                jsonObject.get("symbol").getAsString(),
                jsonObject.get("amount").getAsDouble());
    }

}