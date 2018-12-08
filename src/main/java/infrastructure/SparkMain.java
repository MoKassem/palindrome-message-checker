package infrastructure;

import infrastructure.configs.spark.SparkBase;
import infrastructure.services.RootService;


public class SparkMain extends SparkBase {

    public static void main(String[] args) {
        SparkBase.start(new SparkMain());
    }

    @Override
    public void configure() {
        register(RootService.class);
        ApplicationModule appModule = new ApplicationModule();
        registerModule(appModule);
    }

    @Override
    public int maxThreads() {
        return 100;
    }

    @Override
    public int minThreads() {
        return 8;
    }
}
