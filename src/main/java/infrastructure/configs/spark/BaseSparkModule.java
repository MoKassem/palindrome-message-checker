package infrastructure.configs.spark;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Scopes;

import java.util.List;

public class BaseSparkModule extends AbstractModule implements GuiceExtensions {
    private final GsonBuilder builder;
    private final List<Class<? extends SparkConfig>> sparkConfigs;

    public BaseSparkModule(GsonBuilder builder, List<Class<? extends SparkConfig>> sparkConfigs) {
        this.builder = builder;
        this.sparkConfigs = sparkConfigs;
    }

    protected void configure() {
        this.bind(GsonBuilder.class).toInstance(this.builder);
        this.bind(Gson.class).toProvider(() -> {
            throw new IllegalStateException("Cannot inject class Gson, please use GsonBuilder instead");
        });
        this.sparkConfigs.forEach((config) -> {
            this.bind((Class<SparkConfig>) config).toConstructor(this.constructorOf(config)).in(Scopes.SINGLETON);
        });
        this.bind(BaseSparkModule.SparkInitializer.class).toConstructor(this.constructorOf(BaseSparkModule.SparkInitializer.class)).asEagerSingleton();
        this.bind(BaseSparkModule.class).toInstance(this);
    }

    private static class SparkInitializer {
        public SparkInitializer(Injector injector, BaseSparkModule base) {
            base.sparkConfigs.forEach((config) -> {
                ((SparkConfig)injector.getInstance(config)).configure();
            });
        }
    }
}
