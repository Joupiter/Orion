package fr.orion.api;

import fr.orion.api.benchmark.BenchHandler;
import fr.orion.api.database.DatabaseLoader;
import fr.orion.api.rank.RankRepository;
import fr.orion.api.user.UserRepository;
import fr.orion.api.utils.json.GsonUtils;
import fr.orion.api.utils.Utils;
import fr.orion.api.utils.json.serializer.GsonImplementation;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public interface OrionApi {

    Logger logger = LoggerFactory.getLogger(OrionApi.class);

    DatabaseLoader getDatabaseLoader();

    UserRepository getUserRepository();

    RankRepository getRankRepository();

    BenchHandler getBenchHandler();

    default void registerRegistries() {
        registerRegistries("fr.orion.core.common", "fr.orion.api");
    }

    default void registerRegistries(String... packages) {
        try (ScanResult scanResult = new ClassGraph().enableAnnotationInfo().acceptPackages(packages).scan()) {
            scanResult.getClassesWithAnnotation(GsonImplementation.class).stream()
                    .map(Utils::getClassFromClassInfo)
                    .filter(Objects::nonNull)
                    .forEach(GsonUtils::addRegistry);
        }
    }

    static OrionApi getProvider() {
        return Provider.getProvider();
    }

    static void setProvider(OrionApi api) {
        Provider.setProvider(api);
    }

    class Provider {

        @Getter @Setter
        private static OrionApi provider;

    }

}