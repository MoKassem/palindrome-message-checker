package testing;


import com.mokassem.sql.PooledReadWriteFlyway;
import infrastructure.configs.StubConfig;

import java.util.concurrent.atomic.AtomicBoolean;

public class PooledDataSource {
    private static AtomicBoolean initialized = new AtomicBoolean(false);

    private static PooledReadWriteFlyway flyway;

    public static PooledReadWriteFlyway getDataSource() {
        if (initialized.getAndSet(true)) {
            return flyway;
        }

        flyway = new PooledReadWriteFlyway(new StubConfig().getJdbcConfig());

        flyway.migrate();
        return flyway;
    }
}