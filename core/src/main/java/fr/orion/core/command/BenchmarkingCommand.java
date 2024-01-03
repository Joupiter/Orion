package fr.orion.core.command;

import fr.orion.api.benchmark.Bench;
import fr.orion.api.benchmark.BenchCategory;
import fr.orion.api.benchmark.BenchHandler;
import fr.orion.core.CorePlugin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class BenchmarkingCommand implements CommandExecutor {

    private final CorePlugin plugin;
    private final BenchHandler benchHandler;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Usage: /bench <category> <benchmark>");
            return true;
        }

        getBenchHandler().getCategory(args[0])
                .map(Optional::orElseThrow)
                .subscribe(category -> category.getBenchmark(args[1]).map(Optional::orElseThrow).subscribe(category::run,
                        throwable -> showAvailableBench(sender, category)), throwable -> showAvailableCategory(sender));

        return true;
    }

    private void showAvailableCategory(CommandSender sender) {
        sender.sendMessage("[Benchmark] List of categories: " + String.join(", ", getBenchHandler().getCategories().keySet()));
    }

    private void showAvailableBench(CommandSender sender, BenchCategory category) {
        sender.sendMessage("[Benchmark] List of benchmarks: " + category.getBenchmarks().stream().map(Bench::getName).collect(Collectors.joining(", ")));
    }

}
