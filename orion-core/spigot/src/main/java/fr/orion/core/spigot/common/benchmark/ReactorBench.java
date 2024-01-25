package fr.orion.core.spigot.common.benchmark;

import fr.orion.api.benchmark.Bench;
import fr.orion.api.benchmark.BenchCategory;
import fr.orion.api.economy.Currency;
import fr.orion.api.economy.CurrencyFactory;
import fr.orion.api.economy.Economy;
import fr.orion.api.economy.currency.IntegerCurrency;
import fr.orion.api.user.User;
import fr.orion.api.utils.json.GsonUtils;
import fr.orion.core.common.user.Account;

import java.util.UUID;

public class ReactorBench extends BenchCategory {

    public ReactorBench() {
        super("reactor");
    }

    @Override
    public void addDefaults() {
        addBenchmark(getFirstTest());
    }

    private Bench getFirstTest() {
        return Bench.newBench("test1", bench -> {
            bench.getStopWatch().start();
            bench.notifyEnd();
        });
    }

}