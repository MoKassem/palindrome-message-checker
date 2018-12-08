package infrastructure.configs.spark;

import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import spark.Spark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SparkBase {
    private static final Logger logger = Logger.getLogger(SparkBase.class.getName());
    private final List<AbstractModule> modules = new ArrayList();
    private final GsonBuilder builder = new GsonBuilder().serializeSpecialFloatingPointValues();
    private final List<Class<? extends SparkConfig>> sparkConfigs = new ArrayList();
    private final Map<String, String> baseProperties = new HashMap();
    private final List<Runnable> afterStartRunnables = new ArrayList();
    public Injector injector;

    public SparkBase() {
    }

    public static void start(SparkBase base) {
        base.initialize();
        base.configure();
        base.startup();
        base.run();
    }

    private void initialize() {
        this.populateProperties(this.baseProperties, "/conf/settings.conf");
        this.setPort(Optional.ofNullable(this.baseProperties.get("port")).map(Integer::parseInt).orElse(8080));
        Spark.threadPool(this.maxThreads(), this.minThreads(), this.threadIdleTimeoutMillis());
        Spark.notFound((req, res) -> {
            res.type("application/json");
            return "{\"error\": \"404\", \"message\": \"The requested path could not be found.\"}";
        });
    }

    protected void setPort(Integer port) {
        this.baseProperties.put("port", port.toString());
        Spark.port(port);
    }

    public abstract void configure();

    public int maxThreads() {
        return 8;
    }

    public int minThreads() {
        return 2;
    }

    public int threadIdleTimeoutMillis() {
        return 60000;
    }

    private void startup() {
        this.modules.add(0, new BaseSparkModule(this.gson(), this.sparkConfigs));
        try {
            this.injector = Guice.createInjector(this.modules);
        } catch (Exception var2) {
            logger.log(Level.SEVERE, "Guice Injection Error, stopping Spark: " + var2.getMessage(), var2);
            Spark.stop();
            throw var2;
        }
    }

    protected GsonBuilder gson() {
        return this.builder;
    }


    protected void register(Class<? extends SparkConfig> config) {
        this.sparkConfigs.add(config);
    }

    protected void registerModule(AbstractModule module) {
        this.modules.add(module);
    }

    private void populateProperties(Map<String, String> result, String location) {
        try {
            Properties properties = new Properties();
            properties.load(SparkBase.class.getResourceAsStream(location));
            Iterator var4 = properties.stringPropertyNames().iterator();

            while(var4.hasNext()) {
                String name = (String)var4.next();
                result.put(name, properties.getProperty(name));
            }
        } catch (Exception var6) {
            ;
        }

    }

    private void run() {
        try {
            this.afterStartRunnables.forEach(Runnable::run);
        } catch (Exception var2) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "After Start Runnable Failed", var2);
            Spark.stop();
            throw var2;
        }
    }
}

